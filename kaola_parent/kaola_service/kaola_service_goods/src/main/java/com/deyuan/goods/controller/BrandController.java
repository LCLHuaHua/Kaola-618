package com.deyuan.goods.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;
import com.deyuan.goods.pojo.Brand;
import com.deyuan.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;
    //查询所有品牌信息
    @RequestMapping("/findAll")
    public Result findAll(){
     //   int i=1/0;
      List<Brand> brandList = brandService.findAll();
        System.out.println(brandList);
      return new Result(true, StatusCode.OK,"查询成功",brandList);
    }
    //添加品牌信息
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand){
        brandService.addBrand(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    //修改品牌信息
   /* @PutMapping("/update")
    public Result update(@RequestBody Brand brand){
        brandService.updateBrand(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }*/
    //修改品牌列表
    @PutMapping("/update/{id}")
    public Result update(@RequestBody Brand brand,@PathVariable("id") Integer id){
        brand.setId(id);
        brandService.updateBrand(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    //删除品牌信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id")Integer id){
        brandService.deleteBrand(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    //根据商品名称查询品牌列表
    @RequestMapping("/cate/{cateName}")
    public Result findByCateName(@PathVariable("cateName")String cateName){
        List<Brand> brandList = brandService.findByCateName(cateName);
        return new Result(true, StatusCode.OK,"查询成功",brandList);
    }
}
