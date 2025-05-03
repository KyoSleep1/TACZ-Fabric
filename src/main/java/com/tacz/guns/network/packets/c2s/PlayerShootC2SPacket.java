package com.tacz.guns.network.packets.c2s;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public record PlayerShootC2SPacket () implements HandledPacket<ServerPlayerEntity> {
    public PlayerShootC2SPacket(PacketByteBuf buffer) {
        this();
    }

    @Override
    public void toBuffer(PacketByteBuf buffer) {
    }

    @Override
    public void handle(ServerPlayerEntity sender) {
        IGunOperator.fromLivingEntity(sender).shoot(sender.getMainHandStack(), sender::getPitch, sender::getYaw);
    }
}