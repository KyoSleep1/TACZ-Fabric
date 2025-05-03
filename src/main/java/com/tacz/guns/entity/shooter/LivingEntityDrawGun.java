package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.LogicalSide;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.GunDrawEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.network.NetworkClientHandler;
import com.tacz.guns.network.packets.s2c.event.GunDrawS2CPacket;
import com.tacz.guns.resource.index.CommonGunIndex;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class LivingEntityDrawGun {
    private final LivingEntity shooter;
    private final ShooterDataHolder data;

    public LivingEntityDrawGun(LivingEntity shooter, ShooterDataHolder data) {
        this.shooter = shooter;
        this.data = data;
    }

    public void draw(ItemStack gun) {
        // 重置各个状态
        data.initialData();
        // 更新切枪时间戳
        if (data.drawTimestamp == -1) {
            data.drawTimestamp = System.currentTimeMillis();
        }
        long drawTime = System.currentTimeMillis() - data.drawTimestamp;
        if (drawTime >= 0) {
            // 如果不处于收枪状态，则需要计算收枪时长
            if (drawTime < data.currentPutAwayTimeS * 1000) {
                // 从开始切枪到现在，抬枪的时间小于收枪需要的时间，则按抬枪时间计算。
                data.drawTimestamp = System.currentTimeMillis() + drawTime;
            } else {
                // 从开始切枪到现在，抬枪的时间大于收枪需要的时间，则按收枪时间计算。
                data.drawTimestamp = System.currentTimeMillis() + (long) (data.currentPutAwayTimeS * 1000);
            }
        }
        new GunDrawEvent(shooter, gun, gun, LogicalSide.SERVER).post();
        NetworkClientHandler.GUN_DRAW.sendToSurroundingPlayers(new GunDrawS2CPacket(shooter.getId(), gun,
                gun), shooter);
        updatePutAwayTime(gun);
    }

    public long getDrawCoolDown(ItemStack gun) {
        if (gun == null) {
            return 0;
        }
        if (!(gun.getItem() instanceof IGun iGun)) {
            return 0;
        }
        Identifier gunId = iGun.getGunId(gun);
        Optional<CommonGunIndex> gunIndex = TimelessAPI.getCommonGunIndex(gunId);
        return gunIndex.map(index -> {
            long coolDown = (long) (index.getGunData().getDrawTime() * 1000) - (System.currentTimeMillis() - data.drawTimestamp);
            // 给 5 ms 的窗口时间，以平衡延迟
            coolDown = coolDown - 5;
            if (coolDown < 0) {
                return 0L;
            }
            return coolDown;
        }).orElse(-1L);
    }

    private void updatePutAwayTime(ItemStack gun) {
        IGun iGun = IGun.getIGunOrNull(gun);
        if (iGun != null) {
            Optional<CommonGunIndex> gunIndex = TimelessAPI.getCommonGunIndex(iGun.getGunId(gun));
            data.currentPutAwayTimeS = gunIndex.map(index -> index.getGunData().getPutAwayTime()).orElse(0F);
        } else {
            data.currentPutAwayTimeS = 0;
        }
    }
}
