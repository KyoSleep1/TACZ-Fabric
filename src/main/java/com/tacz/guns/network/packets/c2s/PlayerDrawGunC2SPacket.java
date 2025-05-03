package com.tacz.guns.network.packets.c2s;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerDrawGunC2SPacket implements HandledPacket<ServerPlayerEntity> {

    public PlayerDrawGunC2SPacket(PacketByteBuf buf) {
        this();
    }

    public PlayerDrawGunC2SPacket() {
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
    }

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        PlayerInventory inventory = player.getInventory();
        int selected = inventory.selectedSlot;
        IGunOperator.fromLivingEntity(player).draw(inventory.getStack(selected));
    }
}
