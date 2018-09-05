package com.jinghui.content.service;

import java.util.List;

import com.jinghui.common.utils.E3Result;
import com.jinghui.pojo.TbContent;

public interface ContentService {

	E3Result addContent(TbContent content);
	List<TbContent> getContentListByCid(long cid);
}
