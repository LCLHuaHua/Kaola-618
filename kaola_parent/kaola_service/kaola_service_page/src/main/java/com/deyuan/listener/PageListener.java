package com.deyuan.listener;

import com.deyuan.service.PageService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@RabbitListener(queues = "page_create_queue")
@Component
public class PageListener {
    @Autowired
    private PageService pageService;
    @RabbitHandler
    public void createHtml(String spuId){
        pageService.createDateHtml(spuId);
    }
}
