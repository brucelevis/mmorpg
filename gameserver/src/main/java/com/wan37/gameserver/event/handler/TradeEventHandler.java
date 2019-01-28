package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.TradeEvent;
import com.wan37.gameserver.game.mission.model.QuestCondition;
import com.wan37.gameserver.game.mission.model.QuestType;
import com.wan37.gameserver.game.mission.service.MissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 10:28
 * @version 1.00
 * Description: 交易事件处理器
 */

@Component
public class TradeEventHandler {

    {
        EventBus.subscribe(TradeEvent.class,this::firstTrade);
    }


    @Resource
    private MissionService missionService;

    private void firstTrade(TradeEvent tradeEvent) {
        // 分别检测交易发起者和交易被动者
        missionService.checkMissionProgress(QuestType.TRADE,tradeEvent.getInitiator(), QuestCondition.FIRST_ACHIEVEMENT);
        missionService.checkMissionProgress(QuestType.TRADE,tradeEvent.getAccepter(), QuestCondition.FIRST_ACHIEVEMENT);
    }


}
