package com.deyuan.service.impl;

import com.alibaba.fastjson.JSON;
import com.deyuan.SkuInfo;
import com.deyuan.dao.SearchMapper;
import com.deyuan.goods.fegin.SkuFegin;
import com.deyuan.goods.pojo.Sku;
import com.deyuan.service.EsManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EsManageServiceImpl implements EsManageService {
    //创建索引库
    @Autowired
    private SearchMapper searchMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private SkuFegin skuFegin;

    @Override
    public void createIndexManage() {
        elasticsearchTemplate.createIndex(SkuInfo.class);
    }

    @Override
    public void deleteIndexManage() {
     elasticsearchTemplate.deleteIndex(SkuInfo.class);
    }

    @Override
    public void importBySpuId(String spuId) {
        //1.根据spuId得到SkuList
        List<Sku> skuList = skuFegin.findBySpuId(spuId);
        System.out.println(skuList);
        //把skulist---->skuInfo
        // 1.1 skuList转换成json
        String skujson = JSON.toJSONString(skuList);
        //1.2 把json转换成对象
        List<SkuInfo> skuInfoList = JSON.parseArray(skujson, SkuInfo.class);
        for (SkuInfo skuInfo : skuInfoList) {
            String spec = skuInfo.getSpec();
            Map specMap = JSON.parseObject(spec, Map.class);
            skuInfo.setSpecMap(specMap);
        }
        //2 导入skuInfo
        searchMapper.saveAll(skuInfoList);
    }

    @Override
    public void importAll() {
        List<Sku> skuList = skuFegin.findAll();
        String skujson = JSON.toJSONString(skuList);
        List<SkuInfo> skuInfoList = JSON.parseArray(skujson, SkuInfo.class);
        for (SkuInfo skuInfo : skuInfoList) {
            String spec = skuInfo.getSpec();
            Map specMap = JSON.parseObject(spec, Map.class);
            skuInfo.setSpecMap(specMap);
        }
        searchMapper.saveAll(skuInfoList);
    }
}
