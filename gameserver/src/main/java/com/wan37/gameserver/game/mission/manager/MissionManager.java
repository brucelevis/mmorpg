package com.wan37.gameserver.game.mission.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameserver.game.mission.model.*;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.manager.task.WorkThreadPool;
import com.wan37.gameserver.util.FileUtil;
import com.wan37.mysql.pojo.entity.TMissionProgress;
import com.wan37.mysql.pojo.entity.TMissionProgressExample;
import com.wan37.mysql.pojo.entity.TMissionProgressKey;
import com.wan37.mysql.pojo.mapper.TMissionProgressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/25 18:29
 * @version 1.00
 * Description: 任务成就管理
 */


@Component
@Slf4j
public class MissionManager {

    @Resource
    private TMissionProgressMapper tMissionProgressMapper;

    // 任务成就
    private static Cache<Integer, Quest> missionCache = CacheBuilder.newBuilder()
            .removalListener(
                    notification -> log.info(notification.getKey() + " 任务成就被移除，原因是" + notification.getCause())
            ).build();




    public static Quest getMission(Integer missionId) {
        return missionCache.getIfPresent(missionId);
    }

    public static Map<Integer, Quest> allMission() {
        return missionCache.asMap();
    }






    @PostConstruct
    public void init() {
        loadMission();

    }

    private void loadMission() {
        String path = FileUtil.getStringPath("gameData/quest.xlsx");
        MissionExcelUtil missionExcelUtil = new MissionExcelUtil(path);
        Map<Integer, Quest> missionMap = missionExcelUtil.getMap();

        missionMap.values().forEach(
                mission -> {
                    mission.getConditionsMap();
                    mission.getRewardThingsMap();
                    missionCache.put(mission.getKey(),mission);}
        );
        log.info("任务成就资源加载完毕");
    }


    /**
     * 加载任务成就进度
     * @param player 玩家
     */
    public void loadMissionProgress(Player player) {
        TMissionProgressExample tMissionProgressExample = new TMissionProgressExample();
        tMissionProgressExample.or().andPlayerIdEqualTo(player.getId());
        List<TMissionProgress> tMissionProgressList =  tMissionProgressMapper.selectByExample(tMissionProgressExample);
        Map<Integer, QuestProgress> playerMissionProgressMap = new ConcurrentHashMap<>(15);

        tMissionProgressList.forEach(
                tMP -> {
                    QuestProgress mp = new QuestProgress();
                    mp.setPlayerId(tMP.getPlayerId());
                    mp.setMissionId(tMP.getMissionId());
                    mp.setBeginTime(tMP.getBeginTime());
                    mp.setEndTime(tMP.getEndTime());
                    mp.setMissionState(tMP.getMissionState());
                    // 加载任务实体
                    mp.setQuest(getMission(mp.getMissionId()));
                    // 从数据库获取任务成就进度
                    Map<String, ProgressNumber>  missionProgressMap = JSON.parseObject(tMP.getProgress(),
                            new TypeReference<Map<String,ProgressNumber>>(){});
                    log.debug("========== 从数据库加载的任务进程 {}",  mp);
                    mp.setProgressMap(missionProgressMap);
                    playerMissionProgressMap.put(mp.getMissionId(),mp);
                    player.getMissionProgresses().put(mp.getMissionId(),mp);
                }
        );
        log.debug("玩家任务成就进度数据数据完毕 {}", playerMissionProgressMap);
    }



    /**
     *  创建或更新一个玩家任务成就进度记录
     * @param progress 新创建的进度
     */
    public  void saveOrUpdateMissionProgress(QuestProgress progress) {
        WorkThreadPool.threadPool.execute(() -> {
            TMissionProgressKey key = new TMissionProgressKey();
            key.setMissionId(progress.getMissionId());
            key.setPlayerId(progress.getPlayerId());
            TMissionProgress tMissionProgress = tMissionProgressMapper.selectByPrimaryKey(key);
            if (Objects.isNull(tMissionProgress)) {
                tMissionProgressMapper.insertSelective(progress);
            } else {
                tMissionProgressMapper.updateByPrimaryKeySelective(progress);
            }
        });
    }


    /**
     *  更新瓦加进程
     * @param progress 更新玩家进程
     */
    public void updateMissionProgress(QuestProgress progress) {
        WorkThreadPool.threadPool.execute(() -> tMissionProgressMapper.updateByPrimaryKeySelective(progress) );
    }


}
