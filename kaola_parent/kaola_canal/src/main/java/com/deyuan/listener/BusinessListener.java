package com.deyuan.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;

/**
 * 监听kaola_business这个数据库表的数据变化
 * CanalEntry.EventType eventType  监控数据表中的事件 例如update  insert delete
 * CanalEntry.RowData rowData  表中的数据

 */
@CanalEventListener
public class BusinessListener {
    @ListenPoint(schema = "kaola_business",table = {"tb_ad"})
    public void adUpdate(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        System.out.println("数据发生了改变");
        //修改前

        //修改后
    }
}
