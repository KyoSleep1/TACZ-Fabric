package com.tacz.guns.mixin.server;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.network.NetworkClientHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Mixin(EntityTrackerEntry.class)
public class MixinEntityTrackerEntry {

    @Shadow
    @Final
    private Entity entity;

    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    private @Nullable List<DataTracker.SerializedEntry<?>> changedEntries;

    @Shadow
    @Final
    private boolean alwaysUpdateVelocity;

    @Shadow
    private Vec3d velocity;

    /**
     * @author KyoSleep
     * @reason Hijack entity spawn packet to add custom data on EntityBullet spawn.
     */
    @Overwrite
    public void sendPackets(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> sender) {
        if (this.entity.isRemoved()) {
            LOGGER.warn("Fetching packet for removed entity {}", this.entity);
        }

        if (this.entity instanceof EntityKineticBullet bullet) {
            NetworkClientHandler.SPAWN_BULLET.sendToPlayer(bullet.createCustomSpawnPacket((EntityTrackerEntry)
                    (Object) this), player);
        } else {
            Packet<ClientPlayPacketListener> packet = this.entity.createSpawnPacket((EntityTrackerEntry) (Object) this);
            sender.accept(packet);
        }

        if (this.changedEntries != null) {
            sender.accept(new EntityTrackerUpdateS2CPacket(this.entity.getId(), this.changedEntries));
        }

        boolean bl = this.alwaysUpdateVelocity;
        if (this.entity instanceof LivingEntity) {
            Collection<EntityAttributeInstance> collection = ((LivingEntity) this.entity).getAttributes().getAttributesToSend();
            if (!collection.isEmpty()) {
                sender.accept(new EntityAttributesS2CPacket(this.entity.getId(), collection));
            }

            if (((LivingEntity) this.entity).isFallFlying()) {
                bl = true;
            }
        }

        if (bl && !(this.entity instanceof LivingEntity)) {
            sender.accept(new EntityVelocityUpdateS2CPacket(this.entity.getId(), this.velocity));
        }

        if (this.entity instanceof LivingEntity) {
            List<Pair<EquipmentSlot, ItemStack>> list = Lists.<Pair<EquipmentSlot, ItemStack>>newArrayList();

            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = ((LivingEntity) this.entity).getEquippedStack(equipmentSlot);
                if (!itemStack.isEmpty()) {
                    list.add(Pair.of(equipmentSlot, itemStack.copy()));
                }
            }

            if (!list.isEmpty()) {
                sender.accept(new EntityEquipmentUpdateS2CPacket(this.entity.getId(), list));
            }
        }

        if (!this.entity.getPassengerList().isEmpty()) {
            sender.accept(new EntityPassengersSetS2CPacket(this.entity));
        }

        if (this.entity.hasVehicle()) {
            sender.accept(new EntityPassengersSetS2CPacket(this.entity.getVehicle()));
        }

        if (this.entity instanceof Leashable leashable && leashable.isLeashed()) {
            sender.accept(new EntityAttachS2CPacket(this.entity, leashable.getLeashHolder()));
        }
    }
}
