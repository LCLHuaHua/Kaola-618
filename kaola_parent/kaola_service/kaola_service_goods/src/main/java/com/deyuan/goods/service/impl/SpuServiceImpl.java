package com.deyuan.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.deyuan.goods.dao.*;
import com.deyuan.goods.pojo.*;
import com.deyuan.goods.service.SpuService;
import com.deyuan.utils.IdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class SpuServiceImpl  implements SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void addGoods(Goods goods) {
        //保存spu
      /*  Spu spu = goods.getSpu();
        spu.setId(String.valueOf(idWorker.nextId()));
        spuMapper.insertSelective(spu);*/
        goods.getSpu().setId(String.valueOf(idWorker.nextId()));
        spuMapper.insertSelective(goods.getSpu());
        //保存skuList
        saveSkuList(goods);
    }

    private void saveSkuList(Goods goods) {
        Spu spu = goods.getSpu();
        Integer category3Id = spu.getCategory3Id();
      /*  Category category=new Category();
        category.setId(category3Id);*/
        Category category = categoryMapper.selectByPrimaryKey(category3Id);//打断点
        //查询brandMapper
        Integer brandId = spu.getBrandId();
        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        //关联品牌和分类
        /**
         * 得到brandId和CategoryId
         * 添加中间件表
         */
        if(brand!=null&&category!=null){
          CategroyBrand categroyBrand=new CategroyBrand();
          categroyBrand.setBrandId(brandId);
          categroyBrand.setCategoryId(category3Id);
          int count = categoryBrandMapper.selectCount(categroyBrand);
          if(count==0){
              categoryBrandMapper.insertSelective(categroyBrand);
          }
        }
        List<Sku> skuList = goods.getSkuList();
        if(skuList!=null && skuList.size()>0){
            for (Sku sku : skuList) {
                sku.setId(String.valueOf(idWorker.nextId()));
                sku.setCreateTime(new Date());
                sku.setUpdateTime(new Date());
                sku.setSpuId(spu.getId());
                //处理下spec规格  json数据  判断添加防止非空  {}
                if(StringUtils.isEmpty(sku.getSpec())){
                    sku.setSpec("{}");
                }
                sku.setCategoryId(category3Id);
                sku.setCategoryName(category.getName());
                sku.setBrandName(brand.getName());//添加brand_name
                //首先提取规格中的value
                String name = spu.getName();
                Map<String,String> specMap = JSONObject.parseObject(sku.getSpec(), Map.class);
                Set<String> keys = specMap.keySet();
                for (String key : keys) {
                    //通过key得到value
                    String value = specMap.get(key);//红色
                    //进行拼接 brandName+value
                    name+=" "+value;
                }
                sku.setName(name);
                skuMapper.insertSelective(sku);
            }
        }
    }

    @Override
    public Goods findSpuById(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId); //得到spu的值
        System.out.println(spu);
        Example example=new Example(Sku.class);//得到某个类的对象
        Example.Criteria criteria = example.createCriteria();//Criteria条件构造器
        criteria.andEqualTo("spuId",spuId);
        List<Sku> skuList = skuMapper.selectByExample(example);//得到所有skulist
        System.out.println(skuList);
        Goods goods=new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    @Override
    public void updateGoods(Goods goods) {
        //先修改spu
        spuMapper.updateByPrimaryKeySelective(goods.getSpu());
        //删除skulist
        Example example=new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId",goods.getSpu().getId());
        skuMapper.deleteByExample(example);
        //添加skulist
        saveSkuList(goods);

    }
     //商品审核 只需要改状态0-1 rabbitMQ
    @Override
    public void checkGoods(String spuId) {
         //得到spu对象
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if(spu!=null){
          spu.setStatus("1");
        }
        //修改
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void OffshelfGoods(String spuId) {
        //得到spu对象
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if(spu!=null){
            spu.setIsDelete("0");//0代表物理删除
            spu.setStatus("0");//0代表下架
        }
        //修改
        spuMapper.updateByPrimaryKeySelective(spu);
    }
   //商品上架  status 1 is_marketable 为1
    @Override
    public void onShelfGoods(String spuId) {
        //1 根据spuId查询商品
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //2 设置spu状态为上架状态
        if(spu!=null&&spu.getStatus().equals("1")){
            spu.setIsMarketable("1");
        }
        spuMapper.updateByPrimaryKeySelective(spu);
        //发送消息
        rabbitTemplate.convertAndSend("goods_up_exchange","",spuId);

    }
   //逻辑删除 status 0  is_delete 为1
    @Override
    public void logicGoods(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if(spu!=null){
            spu.setStatus("0");
            spu.setIsMarketable("0");
            spu.setIsDelete("1");
        }
        spuMapper.updateByPrimaryKeySelective(spu);//
    }
    //物理删除   status 0 isdelete为0 isMarketable 0 带着skulist一起删除
    @Transactional //多表删除和修改时候
    @Override
    public void deleteGoods(String spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
      //  if(spu!=null){
            //商品必须为下架商品  上架的商品不能删除
            if(spu.getIsMarketable().equals("1")){
                throw  new RuntimeException("该商品必须下架商品才能删除");
            }
      //  }
        Example example=new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId",spuId);
        skuMapper.deleteByExample(example);
    }

    @Override
    public Spu findPageById(String spuId) {
        return spuMapper.selectByPrimaryKey(spuId);
    }
}
