package com.tacz.guns.network.packets.c2s;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerAimC2SPacket implements HandledPacket<ServerPlayerEntity> {

    private final boolean isAim;

    public PlayerAimC2SPacket(PacketByteBuf buf) {
        this(buf.readBoolean());
    }

    public PlayerAimC2SPacket(boolean isAim) {
        this.isAim = isAim;
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
        buf.writeBoolean(isAim);
    }

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        IGunOperator.fromLivingEntity(player).aim(isAim);
    }
}
