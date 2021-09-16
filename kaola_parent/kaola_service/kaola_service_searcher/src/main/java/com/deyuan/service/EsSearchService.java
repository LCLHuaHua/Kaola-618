package com.deyuan.service;

import java.util.Map;

public interface EsSearchService {
    /**
     * 关键字模糊查询搜索  例如手机
     */
    Map search(Map<String,String> searchMap);
}
