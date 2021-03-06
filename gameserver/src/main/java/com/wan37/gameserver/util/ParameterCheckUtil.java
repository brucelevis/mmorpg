package com.wan37.gameserver.util;


import com.wan37.common.entity.Message;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/20 15:40
 * @version 1.00
 * Description: 参数校验工具
 */
public final class ParameterCheckUtil {



    public static String[] checkParameter(ChannelHandlerContext ctx, Message message,int parameterNumber) {
        String[] args = null;

        args = message.getContent().split("\\s+");
        if (args.length != parameterNumber) {
            NotificationManager.notifyByCtx(ctx,"您输入的参数数目不正确，请重新输入");
        }

        return args;
    }
}
