package com.jinghui.content.service;

import java.util.List;

import com.jinghui.common.pojo.EasyUITreeNode;
import com.jinghui.common.utils.E3Result;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCatList(long parentId);
	E3Result addContentCategory(long parentId, String name);
}
