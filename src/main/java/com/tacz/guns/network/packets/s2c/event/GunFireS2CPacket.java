package com.tacz.guns.network.packets.s2c.event;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.LogicalSide;
import com.tacz.guns.api.event.common.GunFireEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;

public class GunFireS2CPacket implements HandledPacket<PlayerEntity> {

    private final int shooterId;
    private final ItemStack gunItemStack;

    public GunFireS2CPacket(PacketByteBuf buf) {
        this(buf.readVarInt(), buf instanceof RegistryByteBuf registryByteBuf
                ? ItemStack.OPTIONAL_PACKET_CODEC.decode(registryByteBuf) : null);
    }

    public GunFireS2CPacket(int shooterId, ItemStack gunItemStack) {
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
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            doClientEvent(this);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void doClientEvent(GunFireS2CPacket message) {
        ClientWorld level = MinecraftClient.getInstance().world;
        if (level == null) {
            return;
        }
        if (level.getEntityById(message.shooterId) instanceof LivingEntity shooter) {
            GunFireEvent gunFireEvent = new GunFireEvent(shooter, message.gunItemStack, LogicalSide.CLIENT);
            gunFireEvent.post();
        }
    }
}
