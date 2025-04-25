package com.tacz.guns.network.packets.c2s;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerFireSelectC2SPacket implements HandledPacket<ServerPlayerEntity> {

    public PlayerFireSelectC2SPacket(PacketByteBuf buf) {
        this();
    }

    public PlayerFireSelectC2SPacket() {
    }

    @Override
    public void toBuffer(PacketByteBuf buffer) {
    }

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        IGunOperator.fromLivingEntity(player).fireSelect();
    }
}
