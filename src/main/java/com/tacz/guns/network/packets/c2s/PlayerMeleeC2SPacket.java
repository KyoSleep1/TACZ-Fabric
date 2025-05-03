package com.tacz.guns.network.packets.c2s;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerMeleeC2SPacket implements HandledPacket<ServerPlayerEntity> {

    public PlayerMeleeC2SPacket(PacketByteBuf buf) {
        this();
    }

    public PlayerMeleeC2SPacket() {
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
    }

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        IGunOperator.fromLivingEntity(player).melee(player.getMainHandStack());
    }
}
