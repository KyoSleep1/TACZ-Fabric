package com.tacz.guns.network.packets.s2c;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.client.event.SwapItemWithOffHand;
import com.tacz.guns.util.EnvironmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class SwapItemS2CPacket implements HandledPacket<PlayerEntity> {

    public SwapItemS2CPacket(PacketByteBuf buf) {
        this();
    }

    public SwapItemS2CPacket() {
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
    }

    @Override
    public void handle(PlayerEntity ignoredPlayer) {
        if (EnvironmentUtil.isClient()) {
            new SwapItemWithOffHand().post();
        }
    }
}
