package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.LogicalSide;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.api.event.common.GunReloadEvent;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.network.NetworkClientHandler;
import com.tacz.guns.util.item.IItemHandler;
import com.tacz.guns.network.packets.s2c.event.GunReloadS2CPacket;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.GunReloadData;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class LivingEntityReload {
    private final LivingEntity shooter;
    private final ShooterDataHolder data;
    private final LivingEntityDrawGun draw;
    private final LivingEntityShoot shoot;

    public LivingEntityReload(LivingEntity shooter, ShooterDataHolder data, LivingEntityDrawGun draw, LivingEntityShoot shoot) {
        this.shooter = shooter;
        this.data = data;
        this.draw = draw;
        this.shoot = shoot;
    }

    public void reload(ItemStack gun) {
        if (gun == null) {
            return;
        }
        if (!(gun.getItem() instanceof IGun iGun)) {
            return;
        }
        Identifier gunId = iGun.getGunId(gun);
        TimelessAPI.getCommonGunIndex(gunId).ifPresent(gunIndex -> {
            // 检查换弹是否还未完成
            if (data.reloadStateType.isReloading()) {
                return;
            }
            // 检查是否正在开火冷却
            if (shoot.getShootCoolDown(gun) != 0) {
                return;
            }
            // 检查是否在切枪
            if (draw.getDrawCoolDown(gun) != 0) {
                return;
            }
            // 检查是否在拉栓
            if (data.boltCoolDown >= 0) {
                return;
            }
            int currentAmmoCount = iGun.getCurrentAmmoCount(gun);
            int maxAmmoCount = AttachmentDataUtils.getAmmoCountWithAttachment(gun, gunIndex.getGunData());
            // 检查弹药
            if (IGunOperator.fromLivingEntity(shooter).needCheckAmmo() && !inventoryHasAmmo(shooter, currentAmmoCount, maxAmmoCount, gun, iGun)) {
                return;
            }
            // 触发装弹事件
            if (new GunReloadEvent(shooter, gun, LogicalSide.SERVER).post()) {
                return;
            }
            NetworkClientHandler.GUN_RELOAD.sendToSurroundingPlayers(new GunReloadS2CPacket(shooter.getId(),
                    gun), shooter);
            Bolt boltType = gunIndex.getGunData().getBolt();
            int ammoCount = iGun.getCurrentAmmoCount(gun) + (iGun.hasBulletInBarrel(gun) && boltType != Bolt.OPEN_BOLT ? 1 : 0);
            if (ammoCount <= 0) {
                // 初始化空仓换弹的 tick 的状态
                data.reloadStateType = ReloadState.StateType.EMPTY_RELOAD_FEEDING;
            } else {
                // 初始化战术换弹的 tick 的状态
                data.reloadStateType = ReloadState.StateType.TACTICAL_RELOAD_FEEDING;
            }
            data.reloadTimestamp = System.currentTimeMillis();
        });
    }

    public ReloadState tickReloadState(ItemStack gun) {
        // 初始化 tick 返回值
        ReloadState reloadState = new ReloadState();
        reloadState.setStateType(ReloadState.StateType.NOT_RELOADING);
        reloadState.setCountDown(ReloadState.NOT_RELOADING_COUNTDOWN);
        // 判断是否正在进行装填流程。如果没有则返回。
        if (data.reloadTimestamp == -1 || gun == null) {
            return reloadState;
        }
        if (!(gun.getItem() instanceof IGun iGun)) {
            return reloadState;
        }
        // 获取当前枪械的 ReloadData。如果没有则返回。
        Identifier gunId = iGun.getGunId(gun);
        Optional<CommonGunIndex> gunIndexOptional = TimelessAPI.getCommonGunIndex(gunId);
        if (gunIndexOptional.isEmpty()) {
            return reloadState;
        }
        GunData gunData = gunIndexOptional.get().getGunData();
        GunReloadData reloadData = gunData.getReloadData();
        // 计算新的 stateType 和 countDown
        long countDown = ReloadState.NOT_RELOADING_COUNTDOWN;
        ReloadState.StateType stateType = data.reloadStateType;
        long progressTime = System.currentTimeMillis() - data.reloadTimestamp;
        if (stateType.isReloadingEmpty()) {
            long feedTime = (long) (reloadData.getFeed().getEmptyTime() * 1000);
            long finishingTime = (long) (reloadData.getCooldown().getEmptyTime() * 1000);
            if (progressTime < feedTime) {
                stateType = ReloadState.StateType.EMPTY_RELOAD_FEEDING;
                countDown = feedTime - progressTime;
            } else if (progressTime < finishingTime) {
                stateType = ReloadState.StateType.EMPTY_RELOAD_FINISHING;
                countDown = finishingTime - progressTime;
            } else {
                stateType = ReloadState.StateType.NOT_RELOADING;
                data.reloadTimestamp = -1;
            }
        } else if (stateType.isReloadingTactical()) {
            long feedTime = (long) (reloadData.getFeed().getTacticalTime() * 1000);
            long finishingTime = (long) (reloadData.getCooldown().getTacticalTime() * 1000);
            if (progressTime < feedTime) {
                stateType = ReloadState.StateType.TACTICAL_RELOAD_FEEDING;
                countDown = feedTime - progressTime;
            } else if (progressTime < finishingTime) {
                stateType = ReloadState.StateType.TACTICAL_RELOAD_FINISHING;
                countDown = finishingTime - progressTime;
            } else {
                stateType = ReloadState.StateType.NOT_RELOADING;
                data.reloadTimestamp = -1;
            }
        }
        // 更新枪内弹药
        int maxAmmoCount = AttachmentDataUtils.getAmmoCountWithAttachment(gun, gunData);
        if (data.reloadStateType == ReloadState.StateType.EMPTY_RELOAD_FEEDING) {
            if (stateType == ReloadState.StateType.EMPTY_RELOAD_FINISHING) {
                if (iGun instanceof AbstractGunItem abstractGunItem) {
                    abstractGunItem.reloadAmmo(gun, getAndExtractNeedAmmoCount(shooter, gun, iGun, maxAmmoCount), true);
                }
            }
        }
        if (data.reloadStateType == ReloadState.StateType.TACTICAL_RELOAD_FEEDING) {
            if (stateType == ReloadState.StateType.TACTICAL_RELOAD_FINISHING) {
                if (iGun instanceof AbstractGunItem abstractGunItem) {
                    abstractGunItem.reloadAmmo(gun, getAndExtractNeedAmmoCount(shooter, gun, iGun, maxAmmoCount), false);
                }
            }
        }
        // 更新换弹状态缓存
        data.reloadStateType = stateType;
        // 返回 tick 结果
        reloadState.setStateType(stateType);
        reloadState.setCountDown(countDown);
        return reloadState;
    }

    public static boolean inventoryHasAmmo(LivingEntity shooter, int currentAmmoCount, int maxAmmoCount, ItemStack currentGunItem, IGun iGun) {
        // 超出或达到上限，不换弹
        if (currentAmmoCount >= maxAmmoCount) {
            return false;
        }
        if (iGun.useDummyAmmo(currentGunItem)) {
            return iGun.getDummyAmmoAmount(currentGunItem) > 0;
        }
        // ???? 도대체 이거 뭔데?

        return shooter.tacz$getItemHandler(null).map(cap -> {
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(currentGunItem, checkAmmoStack)) {
                    return true;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(currentGunItem, checkAmmoStack)) {
                    return true;
                }
            }
            return false;
        }).orElse(false);
    }

    public static int getAndExtractNeedAmmoCount(LivingEntity shooter, ItemStack currentGunItem, IGun iGun, int maxAmmoCount) {
        int currentAmmoCount = iGun.getCurrentAmmoCount(currentGunItem);
        if (IGunOperator.fromLivingEntity(shooter).needCheckAmmo()) {
            if (iGun.useDummyAmmo(currentGunItem)) {
                return getAndExtractDummyAmmoCount(maxAmmoCount, currentAmmoCount, currentGunItem, iGun);
            }
            return shooter.tacz$getItemHandler(null)
                    .map(cap -> getAndExtractInventoryAmmoCount(cap, maxAmmoCount, currentAmmoCount, currentGunItem))
                    .orElse(currentAmmoCount);
        }
        return maxAmmoCount;
    }

    private static int getAndExtractDummyAmmoCount(int maxAmmoCount, int currentAmmoCount, ItemStack currentGunItem, IGun iGun) {
        int needAmmoCount = maxAmmoCount - currentAmmoCount;
        int dummyAmmoCount = iGun.getDummyAmmoAmount(currentGunItem);
        int extractCount = Math.min(dummyAmmoCount, needAmmoCount);
        iGun.setDummyAmmoAmount(currentGunItem, dummyAmmoCount - extractCount);
        return maxAmmoCount - (needAmmoCount - extractCount);
    }

    private static int getAndExtractInventoryAmmoCount(IItemHandler itemHandler, int maxAmmoCount, int currentAmmoCount, ItemStack currentGunItem) {
        // 子弹数量检查
        int needAmmoCount = maxAmmoCount - currentAmmoCount;
        // 背包检查
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack checkAmmoStack = itemHandler.getStackInSlot(i);
            if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(currentGunItem, checkAmmoStack)) {
                ItemStack extractItem = itemHandler.extractItem(i, needAmmoCount, false);
                needAmmoCount = needAmmoCount - extractItem.getCount();
                if (needAmmoCount <= 0) {
                    break;
                }
            }
            if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(currentGunItem, checkAmmoStack)) {
                int boxAmmoCount = iAmmoBox.getAmmoCount(checkAmmoStack);
                int extractCount = Math.min(boxAmmoCount, needAmmoCount);
                int remainCount = boxAmmoCount - extractCount;
                iAmmoBox.setAmmoCount(checkAmmoStack, remainCount);
                if (remainCount <= 0) {
                    iAmmoBox.setAmmoId(checkAmmoStack, DefaultAssets.EMPTY_AMMO_ID);
                }
                needAmmoCount = needAmmoCount - extractCount;
                if (needAmmoCount <= 0) {
                    break;
                }
            }
        }
        return maxAmmoCount - needAmmoCount;
    }
}
