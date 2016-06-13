package net.wendal.nutzbook.service;

import org.nutz.dao.Cnd;

import java.util.Map;

public interface CndBuilder {
	
	/**
	 * 构建查询条件，用户自定义
	 * @return
	 */
	public Cnd build(Map<String, String> params);
}
