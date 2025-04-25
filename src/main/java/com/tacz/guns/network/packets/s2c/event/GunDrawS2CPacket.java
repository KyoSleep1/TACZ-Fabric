package com.tacz.guns.network.packets.s2c.event;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.LogicalSide;
import com.tacz.guns.api.event.common.GunDrawEvent;
import com.tacz.guns.util.EnvironmentUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;

public class GunDrawS2CPacket implements HandledPacket<PlayerEntity> {

    private final int entityId;
    private final ItemStack previousGunItem;
    private final ItemStack currentGunItem;

    public GunDrawS2CPacket(PacketByteBuf buf) {
        this(buf.readVarInt(), buf instanceof RegistryByteBuf registryByteBuf
                ? ItemStack.OPTIONAL_PACKET_CODEC.decode(registryByteBuf) : null, buf instanceof RegistryByteBuf registryByteBuf
                ? ItemStack.OPTIONAL_PACKET_CODEC.decode(registryByteBuf) : null);
    }

    public GunDrawS2CPacket(int entityId, ItemStack previousGunItem, ItemStack currentGunItem) {
        this.entityId = entityId;
        this.previousGunItem = previousGunItem;
        this.currentGunItem = currentGunItem;
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
        buf.writeVarInt(entityId);
        if (buf instanceof RegistryByteBuf registryByteBuf) {
            ItemStack.OPTIONAL_PACKET_CODEC.encode(registryByteBuf, previousGunItem);
            ItemStack.OPTIONAL_PACKET_CODEC.encode(registryByteBuf, currentGunItem);
        }
    }

    @Override
    public void handle(PlayerEntity ignoredPlayer) {
        if (EnvironmentUtil.isClient()) {
            doClientEvent(this);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void doClientEvent(GunDrawS2CPacket message) {
        ClientWorld level = MinecraftClient.getInstance().world;
        if (level == null) {
            return;
        }
        if (level.getEntityById(message.entityId) instanceof LivingEntity livingEntity) {
            GunDrawEvent gunDrawEvent = new GunDrawEvent(livingEntity, message.previousGunItem, message.currentGunItem, LogicalSide.CLIENT);
            gunDrawEvent.post();
        }
    }
}
