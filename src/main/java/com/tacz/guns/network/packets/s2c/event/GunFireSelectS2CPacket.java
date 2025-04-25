package com.tacz.guns.network.packets.s2c.event;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.LogicalSide;
import com.tacz.guns.api.event.common.GunFireSelectEvent;
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

public class GunFireSelectS2CPacket implements HandledPacket<PlayerEntity> {

    private final int shooterId;
    private final ItemStack gunItemStack;

    public GunFireSelectS2CPacket(PacketByteBuf buf) {
        this(buf.readVarInt(), buf instanceof RegistryByteBuf registryByteBuf ?
                ItemStack.OPTIONAL_PACKET_CODEC.decode(registryByteBuf) : null);
    }

    public GunFireSelectS2CPacket(int shooterId, ItemStack gunItemStack) {
        this.shooterId = shooterId;
        this.gunItemStack = gunItemStack;
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
        buf.writeVarInt(shooterId);
        if (buf instanceof RegistryByteBuf registryByteBuf) {
            ItemStack.OPTIONAL_PACKET_CODEC.encode(registryByteBuf, gunItemStack);
        }
    }

    @Override
    public void handle(PlayerEntity ignoredPlayer) {
        if (EnvironmentUtil.isClient()) {
            doClientEvent(this);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void doClientEvent(GunFireSelectS2CPacket message) {
        ClientWorld level = MinecraftClient.getInstance().world;
        if (level == null) {
            return;
        }
        if (level.getEntityById(message.shooterId) instanceof LivingEntity shooter) {
            GunFireSelectEvent gunFireSelectEvent = new GunFireSelectEvent(shooter, message.gunItemStack, LogicalSide.CLIENT);
            gunFireSelectEvent.post();
        }
    }
}
