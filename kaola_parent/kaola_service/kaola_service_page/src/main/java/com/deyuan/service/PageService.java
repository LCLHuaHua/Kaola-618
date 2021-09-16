package com.deyuan.service;

import java.util.Map;

public interface PageService {
    //根据spuId来构建spu生成静态页面所需要的数据
    Map pageDate(String spuId);

    //生成详情页
    void createDateHtml(String spuId);

}
