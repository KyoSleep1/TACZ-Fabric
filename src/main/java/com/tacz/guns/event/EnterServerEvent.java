package com.tacz.guns.event;

import com.tacz.guns.resource.network.CommonGunPackNetwork;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class EnterServerEvent {

    public static void onLoggedInServer(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        CommonGunPackNetwork.syncClient(handler.player);
    }
}
