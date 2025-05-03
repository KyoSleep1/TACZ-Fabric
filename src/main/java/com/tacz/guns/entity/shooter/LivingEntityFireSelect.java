package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.LogicalSide;
import com.tacz.guns.api.event.common.GunFireSelectEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.network.NetworkClientHandler;
import com.tacz.guns.network.packets.s2c.event.GunFireSelectS2CPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class LivingEntityFireSelect {
    private final LivingEntity shooter;
    private final ShooterDataHolder data;

    public LivingEntityFireSelect(LivingEntity shooter, ShooterDataHolder data) {
        this.shooter = shooter;
        this.data = data;
    }

    public void fireSelect(ItemStack gun) {
        if (gun == null) {
            return;
        }
        if (!(gun.getItem() instanceof IGun iGun)) {
            return;
        }
        if (new GunFireSelectEvent(shooter, gun, LogicalSide.SERVER).post()) {
            return;
        }
        NetworkClientHandler.GUN_FIRE_SELECT.sendToSurroundingPlayers(
                new GunFireSelectS2CPacket(shooter.getId(), gun), shooter);
        if (iGun instanceof AbstractGunItem logicGun) {
            logicGun.fireSelect(gun);
        }
    }
}
