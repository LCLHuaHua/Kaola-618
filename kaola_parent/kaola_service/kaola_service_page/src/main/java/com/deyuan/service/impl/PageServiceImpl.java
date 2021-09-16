package com.deyuan.service.impl;

import com.alibaba.fastjson.JSON;
import com.deyuan.goods.fegin.CategoryFegin;
import com.deyuan.goods.fegin.SkuFegin;
import com.deyuan.goods.fegin.SpuFegin;
import com.deyuan.goods.pojo.Category;
import com.deyuan.goods.pojo.Sku;
import com.deyuan.goods.pojo.Spu;
import com.deyuan.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private SpuFegin spuFegin;
    @Autowired
    private SkuFegin skuFegin;
    @Autowired
    private CategoryFegin categoryFegin;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${pagepath}")
    private String pagepath;

    //构建详情页所需要的数据
    @Override
    public Map pageDate(String spuId) {
        Map pageDate=new HashMap();
        //1、根据spuId来查询spu
        Spu spu = spuFegin.findPageById(spuId);
        pageDate.put("spu",spu);
        if (spu != null) {
            //2、根据spuId来查询skuList
            List<Sku> skuList = skuFegin.findBySpuId(spuId);
            pageDate.put("skuList",skuList);
            //3、根据spu得到三级分类名称
            Category category1 = categoryFegin.findById(spu.getCategory1Id());
            Category category2 = categoryFegin.findById(spu.getCategory2Id());
            Category category3 = categoryFegin.findById(spu.getCategory3Id());
            pageDate.put("category1",category1);
            pageDate.put("category2",category2);
            pageDate.put("category3",category3);
            //4、根据spu的到skuimages  url
            //把map集合转化成list集合
            List<Map> maps = JSON.parseArray(spu.getImages(), Map.class);
            if (maps != null && maps.size() > 0) {
                List<String> imageList = new ArrayList<>();
                for (Map map : maps) {
                    String images = String.valueOf(map.get("url"));
                    imageList.add(images);
                }
                pageDate.put("imageList",imageList);
            }
            //5、根据spu的sku规格列表
            Map specificationList = JSON.parseObject(spu.getSpecItems(), Map.class);
            pageDate.put("specificationList",specificationList);

        }
        return pageDate;
    }

    //生成详情页的
    @Override
    public void createDateHtml(String spuId) {
        //创建模板引擎上下文对象
        Context context=new Context();
        //填充数据
        context.setVariables(pageDate(spuId));

        File file=new File(pagepath);
        if(!file.exists()){
            file.mkdirs();//如果没有这个文件夹就可以直接创建了
        }

        //生成页面
        PrintWriter pw= null;
        try {
            pw = new PrintWriter(pagepath+"/"+spuId+".html");
            templateEngine.process("item",context,pw);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            pw.close();
        }
    }
}
