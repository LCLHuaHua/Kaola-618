package com.deyuan.listener;

import com.deyuan.service.EsManageService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "search_add_queue")
public class SpuAdListener {
    @Autowired
    private EsManageService esManageService;

    @RabbitHandler
    public void searchListener(String spuId){
        esManageService.importBySpuId(spuId);
    }
}
