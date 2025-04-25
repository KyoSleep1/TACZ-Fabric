package com.tacz.guns.network.packets.s2c;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.client.gui.GunRefitScreen;
import com.tacz.guns.util.EnvironmentUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class RefreshRefitScreenS2CPacket implements HandledPacket<PlayerEntity> {

    public RefreshRefitScreenS2CPacket(PacketByteBuf buf) {
        this();
    }

    public RefreshRefitScreenS2CPacket() {
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
    }

    @Override
    public void handle(PlayerEntity ignoredPlayer) {
        if (EnvironmentUtil.isClient()) {
            updateScreen();
        }
    }

    @Environment(EnvType.CLIENT)
    private static void updateScreen() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && MinecraftClient.getInstance().currentScreen instanceof GunRefitScreen screen) {
            screen.init();
        }
    }
}
