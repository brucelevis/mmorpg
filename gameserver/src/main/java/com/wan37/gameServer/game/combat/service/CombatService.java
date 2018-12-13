package com.wan37.gameServer.game.combat.service;

import com.wan37.common.entity.Msg;
import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.gameInstance.service.InstanceService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.gameSceneObject.model.Monster;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameSceneObject.service.MonsterDropsService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.game.skills.service.SkillsService;
import com.wan37.gameServer.game.skills.service.UseSkillsService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TaskManager;
import com.wan37.gameServer.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/8 15:19
 * @version 1.00
 * Description: mmorpg
 */


@Service
@Slf4j
public class CombatService {


    @Resource
    private GameSceneService gameSceneService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private MonsterDropsService monsterDropsService;


    @Resource
    private SkillsService skillsService;

    @Resource
    private TaskManager taskManager;

    @Resource
    private InstanceService instanceService;





    /**
     *  普通攻击服务
     */
    public Msg commonAttack(Player player, Long gameObjectId) {

        GameScene gameScene = gameSceneService.findSceneById(player.getScene());
        Monster target;
        // 地图类型为2，则从玩家所在的副本里取出怪物
        if (gameScene.getType() == 2) {
            gameScene = player.getCurrentGameInstance();
            target = gameScene.getMonsters().get(gameObjectId);
        } else {
            target = gameScene.getMonsters().get(gameObjectId);
        }


        if (target == null) {
            return new Msg(404,"攻击的目标不存在");
        }
        // 攻击力
        int attack = player.getAttack();
        notificationManager.<String>notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format("玩家{0}  向 {1} 发动普通攻击,攻击力为 {2} \n",player.getName(),target.getName(), attack));
        log.debug("玩家的普通攻击力 {}",attack);
        if (target.getState() ==  -1) {
            notificationManager.<String>notifyScenePlayerWithMessage(gameScene,
                    MessageFormat.format("目标 {0} 已经死亡 \n",target.getName()));
            return new Msg(401,"不能攻击，目标已经死亡 \n");
        } else {
            target.setHp(target.getHp() - attack);
            // 重要，设置死亡时间
            target.setDeadTime(System.currentTimeMillis());

            if (target.getHp() <= 0) {
                target.setHp(0L);
                target.setState(-1);
                // 结算掉落，这里暂时直接放到背包里
                monsterDropsService.dropItem(player,target);

                // 如果是副本怪物,获取下一个Boss
                if (gameScene.getType() == 2) {
                    GameInstance gameInstance = (GameInstance) gameScene;
                    // 如果还有boos,下一个boss出场
                    if (gameInstance.getBossList().size()>0) {
                        Monster nextBoss = instanceService.nextBoss(gameObjectId,(GameInstance) gameScene);
                        // boss出场台词
                        notificationManager.notifyScenePlayerWithMessage(gameScene,nextBoss.getTalk());
                    } else {
                        // 所有Boss死亡，挑战成功
                        notificationManager.notifyScenePlayerWithMessage(gameScene,MessageFormat.format(
                                "恭喜你挑战副本{0}成功 ",gameInstance.getName()));
                        instanceService.enterInstance(player,gameInstance.getId());
                    }
                }
            }
            notificationManager.notifyScenePlayerWithMessage(gameScene,
                    MessageFormat.format("{0} 受到{1}的攻击，hp减少{2},当前hp为 {3} \n"
                            ,target.getName(),player.getName(),attack, target.getHp()));
            return new Msg(200,"\n"+player.getName()+"使用普通攻击成功 \n");
        }
    }



    public Msg commonAttackByPVP(Player player, Long targetId) {
        GameScene gameScene = gameSceneService.findSceneByPlayer(player);
        Player targetPlayer = playerDataService.getOnlinePlayerById(targetId);
        // 获取发起攻击者的战力
        int attack = player.getAttack();
        notificationManager.notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format("玩家 {0} 向 {1} 发起攻击，攻击力是{2} \n",
                        player.getName(),targetPlayer.getName(),attack));

        // 结算攻击
        targetPlayer.setHp(targetPlayer.getHp() - attack);

        // 通知攻击结果
        notificationManager.playerBeAttacked(player,targetPlayer, attack);

        // 检测玩家是否死亡
        isPlayerDead(targetPlayer,player);
        return new Msg(200,"\n普通攻击成功\n");
    }


    /**
     *     检测玩家是否死亡
     * @param casualty 伤害承受者
     * @param murderer 攻击发起者
     */
    public void  isPlayerDead(Player casualty, Creature murderer) {

        if (casualty.getHp() < 0){
            casualty.setHp((long)0);
            casualty.setState(-1);

            // 广播并通知死亡的玩家
            notificationManager.playerDead(murderer,casualty);

            gameSceneService.carryToScene(casualty,12);
            notificationManager.notifyPlayer(casualty,casualty.getName()+"  你已经在墓地了,十秒后复活 \n");

            taskManager.schedule(
                    10, () -> {
                        casualty.setState(1);
                        playerDataService.initPlayer(casualty);
                        notificationManager.notifyPlayer(casualty,casualty.getName()+"  你已经复活 \n");
                        return null;
                    }
            );
        }

    }


    /**
     *    使用技能进行pvp,自动识别单体技能和群体技能
     * @param player 玩家
     * @param skillId 技能di
     * @param targetIdList 目标列表
     * @return 返回
     */
    public Msg useSkillPVP(Player player, Integer skillId, List<Long> targetIdList) {


        // 检查技能冷却，
        if (!skillsService.checkCD(player,skillId) ){
            log.debug("player.getSkillMap() {}",player.getSkillMap());
            log.debug("skill {}",skillId);
            return new Msg(404,"你还不能使用该技能，还在冷却中");
        }

        Skill skill = skillsService.getSkill(skillId);
        if ( null == skill) {
             return new Msg(404,"该技能不存在");
        }

        if (targetIdList.size() > 1 && skill.getSkillsType() !=3) {
            return new Msg(401,"该技能不能对多个目标使用");
        }

        if (targetIdList.size() >1) {
            targetIdList.forEach(
                    targetId -> {
                        skillPVP(player,skill,targetId);
                    }
            );
        } else {
            skillPVP(player,skill, targetIdList.get(0));
        }

        return new Msg(200,"使用技能 "+ skill.getName()+" 成功");
    }


    /**
     *  在PVP中使用技能攻击
     * @param player 玩家
     * @param skill 技能
     * @param targetId 目标玩家
     * @return 结果
     */
    public boolean skillPVP(Player player, Skill skill, Long targetId) {


        GameScene gameScene = gameSceneService.findSceneByPlayer(player);
        Player targetPlayer = gameScene.getPlayers().get(targetId);
        if (null == targetPlayer) {
            notificationManager.notifyPlayer(player,"目标不存在此场景，可能已离开或下线");
            return false;
        }

        notificationManager.notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format(" {0}  对 {1} 使用了 {2} 技能",
                        player.getName(),targetPlayer.getName(),skill.getName()));



        if (!skillsService.useSkill(player,targetPlayer,skill)) {
            notificationManager.notifyPlayer(player,"使用技能失败，可能是mp不足");
            return false;
        }

        // 通知攻击结果
        notificationManager.playerBeAttacked(player,targetPlayer, skill.getHpLose());

        // 检测玩家是否死亡
        isPlayerDead(targetPlayer,player);
        return true;
    }




}
