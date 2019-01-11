package com.wan37.gameserver.manager.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameserver.common.IController;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 14:57
 * @version 1.00
 * Description: 控制器管理。 通过msgId映射到其关联的映射器处理。
 */

@Component
@Slf4j
public class ControllerManager {


    @Resource
    private PlayerDataService playerDataService;



    /** MsgId标志和服务之间的映射 */
    private final static Map<MsgId,IController> CONTROLLER_MAP = new ConcurrentHashMap<>();
    
    public static void add(MsgId msgId, IController controller) {
        CONTROLLER_MAP.put(msgId, controller);
    }



    public IController get(int msgId) {
        // 通过int的msgId找到枚举的MsgId
        return CONTROLLER_MAP.get(MsgId.find(msgId,MsgId.UNKNOWN));
    }

    /**
     *
     * @param controller    要执行的任务
     * @param ctx   上下文
     * @param msg 信息
     */
    public void execute(IController controller,ChannelHandlerContext ctx, Message msg) {
        Player player = playerDataService.getPlayerByCtx(ctx);

        // 玩家在场景内则用场景的执行器执行
        Optional.ofNullable(player).map(Player::getCurrentScene).ifPresent(
            scene -> scene.getSingleThreadSchedule().execute(() -> {
                controller.handle(ctx,msg);
                log.debug("{}  {} 的 {} 在执行命令 {}",scene.getSingleThreadSchedule(),
                        scene.getName(),player.getName(),new String(msg.getContent()));
            })
        );

        // 如果用户尚未加载角色
        if (Objects.isNull(player) || Objects.isNull(player.getCurrentScene())) {
            controller.handle(ctx,msg);
        }
    }


}
