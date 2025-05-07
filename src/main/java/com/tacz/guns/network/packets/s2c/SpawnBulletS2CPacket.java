package com.tacz.guns.network.packets.s2c;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.function.BiConsumer;

public class SpawnBulletS2CPacket extends EntitySpawnS2CPacket implements HandledPacket<ClientPlayerEntity> {

    public static final BiConsumer<SpawnBulletS2CPacket, RegistryByteBuf> writeConsumer = (
            bullet, buf) -> {
        buf.writeFloat(bullet.pitch);
        buf.writeFloat(bullet.yaw);
        buf.writeDouble(bullet.velocity.getX());
        buf.writeDouble(bullet.velocity.getY());
        buf.writeDouble(bullet.velocity.getZ());
        buf.writeIdentifier(bullet.gunId);
        buf.writeIdentifier(bullet.ammoId);
        buf.writeFloat(bullet.gravity);
        buf.writeBoolean(bullet.hasExplosion);
        buf.writeBoolean(bullet.hasIgnite);
        buf.writeFloat(bullet.explosionRadius);
        buf.writeFloat(bullet.explosionDamage);
        buf.writeInt(bullet.life);
        buf.writeFloat(bullet.speed);
        buf.writeFloat(bullet.friction);
        buf.writeInt(bullet.pierce);
        buf.writeBoolean(bullet.isTracerAmmo);
    };

    public static final BiConsumer<SpawnBulletS2CPacket, RegistryByteBuf> readConsumer = (
            bullet, buf) -> {
        bullet.pitch = buf.readFloat();
        bullet.yaw = buf.readFloat();
        bullet.velocity = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        bullet.gunId = buf.readIdentifier();
        bullet.ammoId = buf.readIdentifier();
        bullet.gravity = buf.readFloat();
        bullet.hasExplosion = buf.readBoolean();
        bullet.hasIgnite = buf.readBoolean();
        bullet.explosionRadius = buf.readFloat();
        bullet.explosionDamage = buf.readFloat();
        bullet.life = buf.readInt();
        bullet.speed = buf.readFloat();
        bullet.friction = buf.readFloat();
        bullet.pierce = buf.readInt();
        bullet.isTracerAmmo = buf.readBoolean();
    };

    public static final BiConsumer<SpawnBulletS2CPacket, EntityKineticBullet> syncConsumer =
            (packet, bullet) -> {
                bullet.setPitch(packet.pitch);
                bullet.setYaw(packet.yaw);
                bullet.setVelocity(packet.velocity);
                bullet.gunId = packet.gunId;
                bullet.ammoId = packet.ammoId;
                bullet.gravity = packet.gravity;
                bullet.hasExplosion = packet.hasExplosion;
                bullet.hasIgnite = packet.hasIgnite;
                bullet.explosionRadius = packet.explosionRadius;
                bullet.explosionDamage = packet.explosionDamage;
                bullet.life = packet.life;
                bullet.speed = packet.speed;
                bullet.friction = packet.friction;
                bullet.pierce = packet.pierce;
                bullet.isTracerAmmo = packet.isTracerAmmo;
            };

    public Identifier gunId;
    public Identifier ammoId;
    public float gravity;
    public boolean hasExplosion;
    public boolean hasIgnite;
    public float explosionRadius;
    public float explosionDamage;
    public int life;
    public float speed;
    public float friction;
    public int pierce;
    public boolean isTracerAmmo;
    public float pitch;
    public float yaw;
    public Vec3d velocity;

    public SpawnBulletS2CPacket(EntityKineticBullet entity, EntityTrackerEntry entityTrackerEntry, int entityData) {
        super(entity, entityTrackerEntry, entityData);
        this.gunId = entity.getGunId();
        this.ammoId = entity.ammoId;
        this.gravity = entity.gravity;
        this.hasExplosion = entity.hasExplosion;
        this.hasIgnite = entity.hasIgnite;
        this.explosionRadius = entity.explosionRadius;
        this.explosionDamage = entity.explosionDamage;
        this.life = entity.life;
        this.speed = entity.speed;
        this.friction = entity.friction;
        this.pierce = entity.pierce;
        this.isTracerAmmo = entity.isTracerAmmo;
        this.pitch = entity.getPitch();
        this.yaw = entity.getYaw();
        this.velocity = entity.getVelocity();
    }

    @Override
    public void toBuffer(PacketByteBuf packetByteBuf) {
        this.write((RegistryByteBuf) packetByteBuf);
        SpawnBulletS2CPacket.writeConsumer.accept(this, (RegistryByteBuf) packetByteBuf);
    }

    public SpawnBulletS2CPacket(RegistryByteBuf buf) {
        super(buf);
        SpawnBulletS2CPacket.readConsumer.accept(this, buf);
    }

    @Override
    public void handle(ClientPlayerEntity player) {
        player.networkHandler.onEntitySpawn(this);
    }
}
