package com.deyuan.goods.service;

import com.deyuan.goods.pojo.Goods;
import com.deyuan.goods.pojo.Spu;

public interface SpuService {
    //添加商品
    void addGoods(Goods goods);
    //根据spuId来查询spu和sku列表
    Goods findSpuById(String spuId);
    //根据spuId修改spu和sku列表
    void updateGoods(Goods goods);
    //商品审核 根据spuId
    void checkGoods(String spuId);
    //商品下架
    void OffshelfGoods(String spuId);
    //商品上架
    void onShelfGoods(String spuId);
    //逻辑删除
    void logicGoods(String spuId);
    //物理删除 --状态修改 带着skulist一起删除
    void deleteGoods(String spuId);

    Spu findPageById(String spuId);
}
