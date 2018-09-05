package com.jinghui.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinghui.common.enums.ERedisKey;
import com.jinghui.common.jedis.JedisClient;
import com.jinghui.common.utils.E3Result;
import com.jinghui.common.utils.JsonUtils;
import com.jinghui.content.service.ContentService;
import com.jinghui.mapper.TbContentMapper;
import com.jinghui.pojo.TbContent;
import com.jinghui.pojo.TbContentExample;
import com.jinghui.pojo.TbContentExample.Criteria;

/**
 * 内容管理Service
 * @author Administrator
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;

	@Override
	public E3Result addContent(TbContent content) {
		// 将内容数据插入到内容表，根据分类id作为field,保存该类别的list
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 插入到数据库
		contentMapper.insert(content);
		// 缓存同步,删除缓存的数据，保证下次取的是最新的
		jedisClient.hdel(ERedisKey.CONTENT_LIST.getKey(), content.getCategoryId().toString());
		return E3Result.ok();
	}

	/**
	 * 根据内容分类id查询内容列表
	 */
	@Override
	public List<TbContent> getContentListByCid(long cid) {
		// 查询缓存,捕获错误,不影响正常逻辑
		try {
			String json = jedisClient.hget(ERedisKey.CONTENT_LIST.getKey(), cid + "");
			// 如果缓存中有数据直接相应
			if(StringUtils.isNoneBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 没有则查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		// 执行查询，获取特殊字段text
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		// 把结果添加到缓存
		try {
			jedisClient.hset(ERedisKey.CONTENT_LIST.getKey(), cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
