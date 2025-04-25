package com.tacz.guns.network.packets.c2s;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.inventory.GunSmithTableMenu;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CraftC2SPacket implements HandledPacket<ServerPlayerEntity> {

    private final Identifier recipeId;
    private final int menuId;

    public CraftC2SPacket(PacketByteBuf buf) {
        this(buf.readIdentifier(), buf.readVarInt());
    }

    public CraftC2SPacket(Identifier recipeId, int menuId) {
        this.recipeId = recipeId;
        this.menuId = menuId;
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
        buf.writeIdentifier(recipeId);
        buf.writeVarInt(menuId);
    }

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        if (player.currentScreenHandler.syncId == menuId && player.currentScreenHandler instanceof GunSmithTableMenu menu) {
            menu.doCraft(recipeId, player);
        }
    }
}
