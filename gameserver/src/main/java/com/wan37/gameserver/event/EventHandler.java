package com.wan37.gameserver.event;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/19 17:32
 * @version 1.00
 * Description: 事件处理接口
 */

@FunctionalInterface
public interface EventHandler<E extends Event> {
     void handle(E event);
}
