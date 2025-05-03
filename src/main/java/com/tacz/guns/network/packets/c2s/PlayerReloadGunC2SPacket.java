package com.tacz.guns.network.packets.c2s;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public record PlayerReloadGunC2SPacket() implements HandledPacket<ServerPlayerEntity> {
    public PlayerReloadGunC2SPacket(PacketByteBuf buf) {
        this();
    }

    public PlayerReloadGunC2SPacket() {
    }

    @Override
    public void toBuffer(PacketByteBuf buffer) {
    }

    @Override
    public void handle(ServerPlayerEntity sender) {
        if (sender == null) return;
        IGunOperator.fromLivingEntity(sender).reload(sender.getMainHandStack());
    }
}
