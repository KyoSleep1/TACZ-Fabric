package com.tacz.guns.network.packets.c2s;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerBoltGunC2SPacket implements HandledPacket<ServerPlayerEntity> {

    public PlayerBoltGunC2SPacket(PacketByteBuf buf) {
        this();
    }

    public PlayerBoltGunC2SPacket() {
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
    }

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        IGunOperator.fromLivingEntity(player).bolt();
    }
}
