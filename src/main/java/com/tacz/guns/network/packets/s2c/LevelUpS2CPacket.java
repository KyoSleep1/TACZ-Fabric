package com.tacz.guns.network.packets.s2c;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.util.EnvironmentUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;

public class LevelUpS2CPacket implements HandledPacket<PlayerEntity> {

    private final ItemStack gun;
    private final int level;

    public LevelUpS2CPacket(PacketByteBuf buf) {
        this(buf instanceof RegistryByteBuf registryByteBuf ? ItemStack.OPTIONAL_PACKET_CODEC.decode(registryByteBuf)
                : null, buf.readInt());
    }

    public LevelUpS2CPacket(ItemStack gun, int level) {
        this.gun = gun;
        this.level = level;
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
        if (buf instanceof RegistryByteBuf registryByteBuf) {
            ItemStack.OPTIONAL_PACKET_CODEC.encode(registryByteBuf, gun);
        }
        buf.writeInt(level);
    }

    @Override
    public void handle(PlayerEntity ignoredPlayer) {
        if (EnvironmentUtil.isClient()) {
            onLevelUp(this);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void onLevelUp(LevelUpS2CPacket message) {
        int level = message.getLevel();
        ItemStack gun = message.getGun();
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }
        // TODO After completing the gun upgrade logic, unblock the following code
                /*
                if (GunLevelManager.DAMAGE_UP_LEVELS.contains(level)) {
                    Minecraft.getInstance().getToasts().addToast(new GunLevelUpToast(gun,
                            Component.translatable("toast.tacz.level_up"),
                            Component.translatable("toast.tacz.sub.damage_up")));
                } else if (level >= GunLevelManager.MAX_LEVEL) {
                    Minecraft.getInstance().getToasts().addToast(new GunLevelUpToast(gun,
                            Component.translatable("toast.tacz.level_up"),
                            Component.translatable("toast.tacz.sub.final_level")));
                } else {
                    Minecraft.getInstance().getToasts().addToast(new GunLevelUpToast(gun,
                            Component.translatable("toast.tacz.level_up"),
                            Component.translatable("toast.tacz.sub.level_up")));
                }*/
    }

    public ItemStack getGun() {
        return this.gun;
    }

    public int getLevel() {
        return this.level;
    }
}
