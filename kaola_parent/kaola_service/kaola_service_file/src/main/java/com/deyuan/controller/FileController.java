package com.deyuan.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;
import com.deyuan.utils.FastDFSClient;
import com.deyuan.utils.FastDFSFile;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class FileController {

    //文件上传
    @RequestMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file){
        try {
            //1、获取文件名对象
            String originalFilename = file.getOriginalFilename();
            //2、获取文件扩展名 1.txt.jpg
            int index = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(index);
            //3、获取文件内容
            byte[] content= file.getBytes();
            //4、获取URL地址  trackerip地址+组名+虚拟路径+图片对象
            FastDFSFile fastDFSFile=new FastDFSFile(originalFilename,content,extName);
            //获取fastDFSFile文件对象  可以调用upload

            String[] uploadResults = FastDFSClient.upload(fastDFSFile);//文件上传

            //获取组名
            String groupName = uploadResults[0];
            //获取文件存储路径
            String remoteFileName = uploadResults[1];
            String url=FastDFSClient.getTrackerUrl()+groupName+"/"+remoteFileName;//地址+组名+虚拟路径地址
            return  new Result(true, StatusCode.OK,"上传成功",url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  new Result(true, StatusCode.OK,"上传失败");
    }
}
