package com.tacz.guns.network.packets.s2c;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.client.gui.GunSmithTableScreen;
import com.tacz.guns.util.EnvironmentUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class CraftS2CPacket implements HandledPacket<PlayerEntity> {

    private final int menuId;

    public CraftS2CPacket(PacketByteBuf buf) {
        this(buf.readVarInt());
    }

    public CraftS2CPacket(int menuId) {
        this.menuId = menuId;
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
        buf.writeVarInt(menuId);
    }

    @Override
    public void handle(PlayerEntity ignoredPlayer) {
        if (EnvironmentUtil.isClient()) {
            updateScreen(menuId);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void updateScreen(int containerId) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && player.currentScreenHandler.syncId == containerId && MinecraftClient.getInstance()
                .currentScreen instanceof GunSmithTableScreen screen) {
            screen.updateIngredientCount();
        }
    }
}
