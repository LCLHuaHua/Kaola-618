package com.deyuan.service.impl;

import com.alibaba.fastjson.JSON;
import com.deyuan.SkuInfo;
import com.deyuan.service.EsSearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EsSearchServiceImpl implements EsSearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //需求1按照关键字模糊查询
    @Override
    public Map search(Map<String, String> searchMap) {
        Map resultMap = new HashMap<>();
        if (searchMap == null) {
            return resultMap;
        }
        //1.定义组合搜索
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        /*
        must  and
        must not  非
        should or
         */
        if (StringUtils.isNotEmpty(searchMap.get("keywords"))) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", searchMap.get("keywords")).operator(Operator.AND));
        }

        //按照品牌名称精准搜索
        if (StringUtils.isNotEmpty(searchMap.get("brand"))) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("brandName", searchMap.get("brand")));
        }

        //按照分类名称精准搜索
        if (StringUtils.isNotEmpty(searchMap.get("categoryName"))) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("categoryName", searchMap.get("categoryName")));
        }


        //2.定义顶级对象搜索
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);//相当于set方法
        //3.定义执行搜索  nativeSearchQueryBuilder.build() 构建nativeSearchQueryBuilder对象
        //AggregatedPage<SkuInfo> searchResult = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);
        //按照规格名称进行精准搜索 类似mysql  select * from tb_sku where spec_name=‘红色’
        if (searchMap.size() > 0) {
            //遍历searchmap集合
            for (String key : searchMap.keySet()) {
                if (key.startsWith("spec_")) {
                    String specName = key.substring(5);
                    System.out.println(specName);
                    //key spec_color spec_name
                    // String speckey = substring;
                    //value
                    String specVal = searchMap.get(key);
                    boolQueryBuilder.filter(QueryBuilders.termQuery("specMap." + specName + ".keyword", specVal));
                }
            }
        }
        //根据价格范围来搜索   1000-3000
        if (StringUtils.isNotEmpty(searchMap.get("price"))) {
            String price = searchMap.get("price");  //1000-3000之间 【1000,3000】
            String[] split = price.split("-");
            if (split.length == 2) {
                Integer lowprice = Integer.valueOf(split[0]);//lowprice最小值
                Integer highprice = Integer.valueOf(split[1]);//highprice最大值   gte大于等于  lte小于等于
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(lowprice).lte(highprice));
            }
        }
        //根据综合进行排序
        if (StringUtils.isNotEmpty(searchMap.get("sortField")) && StringUtils.isNotEmpty(searchMap.get("sortRule"))) {
            String sortField = searchMap.get("sortField");// 排序字段
            String sortRule = searchMap.get("sortRule");  //排序规则 asc desc
            if ("DESC".equalsIgnoreCase(sortRule)) {
                //  boolQueryBuilder.filter(QueryBuilders.termQuery()) 组合搜索
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.DESC));
            } else {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.ASC));
            }
        }
        int pageNum = 1;
        int pageSize = 60;
        //根据搜索分页
        if (searchMap.size() > 0) {

            if (StringUtils.isNotEmpty(searchMap.get("pageNum")) && StringUtils.isNotEmpty(searchMap.get("pageSize"))) {
                //得到当前页
                pageNum = Integer.valueOf(searchMap.get("pageNum")); //string --->integer---->int
                //得到每页显示条数 60条
                pageSize = Integer.valueOf(searchMap.get("pageSize"));//String ---->integer---->int
                //es 第一页是0
                nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNum - 1, pageSize));
            }
        }
        //搜索关键字高亮显示
        HighlightBuilder.Field highlightName = new HighlightBuilder.Field("name").preTags("<span style='red'>").postTags("</span>");
        nativeSearchQueryBuilder.withHighlightFields(highlightName);


        //需求2 按品牌类型来查询  类似于select * from tb_sku where brand_name='小米'
            String barndGroup = "brandGroup";
            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(barndGroup).field("brandName");
            nativeSearchQueryBuilder.addAggregation(aggregationBuilder);
            //需求3 按照分类来查询 类似于select * from tb_sku where categrouyName='二手手机
            String categoryGroup = "categoryGroup";
            TermsAggregationBuilder categoryName = AggregationBuilders.terms(categoryGroup).field("categoryName");
            nativeSearchQueryBuilder.addAggregation(categoryName);
            //需求4 按照规格来查询
            String specGroup = "specGroup";
            TermsAggregationBuilder specGroupBulider = AggregationBuilders.terms(specGroup).field("spec.keyword");
            nativeSearchQueryBuilder.addAggregation(specGroupBulider);

            AggregatedPage<SkuInfo> searchResult = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                    SearchHit[] hits = searchResponse.getHits().getHits();//结果集的命中
                    long total = searchResponse.getHits().getTotalHits();
                    List<T> skuList = new ArrayList<>();
                    for (SearchHit hit : hits) {
                        String skujson = hit.getSourceAsString();
                        SkuInfo skuInfo = JSON.parseObject(skujson, SkuInfo.class);
                        //高亮显示
                        String skuName = hit.getHighlightFields().get("name").getFragments()[0].toString();
                        skuInfo.setName(skuName);
                        skuList.add((T) skuInfo);
                    }
                    return new AggregatedPageImpl<>(skuList, pageable, total, searchResponse.getAggregations());
                }
            });
            //返回结果
            //按照品牌名称来查询结果
            StringTerms brandTerms = (StringTerms) searchResult.getAggregation(barndGroup);
            List<StringTerms.Bucket> buckets = brandTerms.getBuckets();
            List<String> brandList = new ArrayList<>();
            if (buckets != null && buckets.size() > 0) {
                for (StringTerms.Bucket bucket : buckets) {
                    String brandName = bucket.getKeyAsString();//得到分组名字
                    brandList.add(brandName);
                }
            }
            //按照分类名称来查询
            StringTerms cateGoryTerms = (StringTerms) searchResult.getAggregation(categoryGroup);
            List<StringTerms.Bucket> catebuckets = cateGoryTerms.getBuckets();
            List<String> cateList = new ArrayList<>();
            if (catebuckets != null && catebuckets.size() > 0) {
                cateList = catebuckets.stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
            }
            //按照规格名称来查询
            StringTerms specGroupTerms = (StringTerms) searchResult.getAggregation(specGroup);
            List<StringTerms.Bucket> specBuckets = specGroupTerms.getBuckets();
            /**
             * 数据库存储格式
             * {'颜色': '灰色', '尺码': '200度'}--map集合  set集合 new hashSet()
             * {'颜色': '灰色', '尺码': '300度'}
             * {'颜色': '灰色', '尺码': '150度'}
             * {'颜色': '紫色', '尺码': '300度'}
             * {'颜色': '黑色', '尺码': '100度'}
             *
             * 最终展示给用户的页面
             * {
             *     "颜色"：['灰色','紫色'，'黑色']，
             *     "尺码"：['200度','300度','150度','100度',]
             *     "内存"：['4GB','6GB','8GB']
             * }
             */
            Map<String, Set<String>> specMap = new HashMap<>();
            if (specBuckets != null && specBuckets.size() > 0) {
                for (StringTerms.Bucket specBucket : specBuckets) {
                    String spec = specBucket.getKeyAsString();//spec---{'颜色': '灰色', '尺码': '200度'}
                    Map<String, String> map = JSON.parseObject(spec, Map.class);//转成map对象
                    if (map.size() > 0) {
                        //遍历map
                        Set<String> set = null;
                        for (String key : map.keySet()) {
                            //name
                            String specName = key;
                            //value
                            String specValue = map.get(key);
                            if (!specMap.containsKey(key)) {
                                set = new HashSet<>();//如果不包含可以
                            } else {
                                set = specMap.get(key);
                            }
                            //添加到set集合
                            set.add(specValue);
                            specMap.put(key, set);
                        }
                    }
                }
            }
            resultMap.put("rows", searchResult.getContent());//返回的结果列表
            resultMap.put("total", searchResult.getTotalElements());//返回结果的条数
            resultMap.put("totalPage", searchResult.getTotalPages());//结果页数  默认每页显示10条
            resultMap.put("brandList", brandList);
            resultMap.put("cateList", cateList);
            resultMap.put("specList", specMap);
            resultMap.put("pageNum",pageNum);
            resultMap.put("pageSize",pageSize);

            return resultMap;
        }
    }

