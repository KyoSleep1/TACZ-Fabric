package com.tacz.guns.network.packets.s2c;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.client.resource.ClientReloadManager;
import com.tacz.guns.resource.network.CommonGunPackNetwork;
import com.tacz.guns.resource.network.DataType;
import com.tacz.guns.util.EnvironmentUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.Map;

public class SyncGunPackS2CPacket implements HandledPacket<PlayerEntity> {

    private final EnumMap<DataType, Map<Identifier, String>> cache;

    public SyncGunPackS2CPacket(PacketByteBuf buf) {
        this(CommonGunPackNetwork.fromNetworkCache(buf));
    }

    public SyncGunPackS2CPacket(EnumMap<DataType, Map<Identifier, String>> cache) {
        this.cache = cache;
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
        CommonGunPackNetwork.toNetwork(cache, buf);
    }

    @Override
    public void handle(PlayerEntity ignoredPlayer) {
        if (EnvironmentUtil.isClient()) {
            doSync(this);
        }
    }

    public EnumMap<DataType, Map<Identifier, String>> getCache() {
        return cache;
    }

    @Environment(EnvType.CLIENT)
    private static void doSync(SyncGunPackS2CPacket message) {
        ClientReloadManager.cacheAll(message);
        ClientReloadManager.reloadAllPack();
    }
}
