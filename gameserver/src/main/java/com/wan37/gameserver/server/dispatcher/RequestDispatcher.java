package com.wan37.gameserver.server.dispatcher;

import com.wan37.gameserver.common.IController;
import com.wan37.common.entity.Message;
import com.wan37.gameserver.common.ErrorController;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.game.player.service.PlayerQuitService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/18 17:02
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@ChannelHandler.Sharable
@Component
public class RequestDispatcher  extends SimpleChannelInboundHandler<Message> {


    @Resource
    private ControllerManager controllerManager;

    @Resource
    private ErrorController errorController;

    @Resource
    private PlayerQuitService playerQuitService;


    /**
     *  当客户端连上服务器的时候触发此函数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端: " + ctx.channel().id() + " 加入连接",CharsetUtil.UTF_8);

    }

    /**
     * 当客户端断开连接的时候触发函数
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush("正在断开连接");

        // 将角色信息保存到数据库
        playerQuitService.savePlayer(ctx);

        // 清除缓存
        playerQuitService.cleanPlayerCache(ctx);
        log.info("客户端: " + ctx.channel().id() + " 已经离线");

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        IController controller = controllerManager.get(msg.getMsgId());
        if (controller == null) {
            errorController.handle( ctx ,msg);
        } else {
            controllerManager.execute(controller,ctx,msg);
        }
     }

    /**
     *  玩家意外退出时保存是数据
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {

        NotificationManager.notifyByCtx(ctx,"出现了点小意外"+cause.getMessage());
        log.error("服务器内部发生错误");

        // 将角色信息保存到数据库
        playerQuitService.savePlayer(ctx);

        log.error("发生错误 {}", cause.getMessage());

        // 打印错误
        cause.printStackTrace();

    }


}