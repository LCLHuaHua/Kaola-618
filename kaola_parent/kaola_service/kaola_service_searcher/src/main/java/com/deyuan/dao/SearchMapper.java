package com.deyuan.dao;

import com.deyuan.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchMapper extends ElasticsearchRepository<SkuInfo,Long> {
}
