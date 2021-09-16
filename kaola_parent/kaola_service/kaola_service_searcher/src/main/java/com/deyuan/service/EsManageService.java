package com.deyuan.service;

public interface EsManageService {
    //创建索引库
    void createIndexManage();
    //删除索引库
    void deleteIndexManage();
    //导入索引库
    void importBySpuId(String spuId);
    //导入所有索引库
    void importAll();
}
