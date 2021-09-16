package com.deyuan.goods.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;
import com.deyuan.goods.pojo.Goods;
import com.deyuan.goods.pojo.Spu;
import com.deyuan.goods.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    //添加商品
    @RequestMapping("/addGoods")
    public Result addGoods(@RequestBody Goods goods) {
        spuService.addGoods(goods);
        return new Result(true, StatusCode.OK, "添加商品成功");
    }

    //根据ID查询skuList和spu
    @RequestMapping("/findById/{spuId}")
    public Result findById(@PathVariable("spuId") String spuId) {
        Goods goods = spuService.findSpuById(spuId);
        System.out.println(goods);
        return new Result(true, StatusCode.OK, "查询商品", goods);
    }

    @RequestMapping("/updateGoods")
    public Result updateGoods(@RequestBody Goods goods) {
        spuService.updateGoods(goods);
        return new Result(true, StatusCode.OK, "修改商品成功");

    }

    //商品审核上架-
    @RequestMapping("/checkGoods/{spuId}")
    public Result checkGoods(@PathVariable("spuId") String spuId) {
        spuService.checkGoods(spuId);
        return new Result(true, StatusCode.OK, "商品审核通过");
    }

    @RequestMapping("/Offshelf/{spuId}")
    public Result Offshelf(@PathVariable("spuId") String spuId) {
        spuService.OffshelfGoods(spuId);
        return new Result(true, StatusCode.OK, "商品下架成功");
    }

    @RequestMapping("/onshelf/{spuId}")
    public Result onshelf(@PathVariable("spuId") String spuId) {
        spuService.onShelfGoods(spuId);
        return new Result(true, StatusCode.OK, "商品上架成功");
    }

    @RequestMapping("/logicGoods/{spuId}")
    public Result logicGoods(@PathVariable("spuId") String spuId) {
        spuService.logicGoods(spuId);
        return new Result(true, StatusCode.OK, "逻辑删除成功");
    }

    @RequestMapping("/deleteGoods/{spuId}")
    public Result deleteGoods(@PathVariable("spuId") String spuId) {
        spuService.deleteGoods(spuId);
        return new Result(true, StatusCode.OK, "物理删除成功");
    }

    //根据spuId查询spu
    @RequestMapping("/findPageById/{spuId}")
    public Spu findPageById(@PathVariable("spuId") String spuId) {
        Spu spu = spuService.findPageById(spuId);
        return spu;
    }
}
