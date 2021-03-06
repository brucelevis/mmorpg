package com.wan37.gameserver.game.friend.controller;

import com.wan37.common.entity.Cmd;
import com.wan37.common.entity.Message;
import com.wan37.gameserver.game.friend.service.FriendService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/3 18:24
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class FriendController {

    {
        ControllerManager.add(Cmd.FRIEND_LIST,this::friendList);
        ControllerManager.add(Cmd.FRIEND_ADD,this::friendAdd);
    }


    @Resource
    private FriendService friendService;

    @Resource
    private PlayerDataService playerDataService;




    private void friendAdd(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Long friendId = Long.valueOf(args[1]);
        Player player = playerDataService.getPlayerByCtx(ctx);
        friendService.friendAdd(player,friendId);
    }

    private void friendList(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        friendService.friendList(player);
    }


}
