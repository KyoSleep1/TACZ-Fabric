package com.tacz.guns.init;

import com.mojang.serialization.MapCodec;
import com.tacz.guns.GunMod;
import com.tacz.guns.particles.BulletHoleOption;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final ParticleType<BulletHoleOption> BULLET_HOLE = register("bullet_hole",
            new ModParticleType<>(false, BulletHoleOption.DESERIALIZER, BulletHoleOption.CODEC));

    public static void init() {
    }

    private static <T extends ParticleEffect> ParticleType<T> register(String path, ParticleType<T> type) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(GunMod.MOD_ID, path), type);
    }

    private static class ModParticleType<T extends ParticleEffect> extends ParticleType<T> {

        private final PacketCodec<? super RegistryByteBuf, T> packetCodec;
        private final MapCodec<T> codec;

        public ModParticleType(boolean overrideLimier, PacketCodec<? super RegistryByteBuf, T> packetCodec, MapCodec<T> codec) {
            super(overrideLimier);
            this.packetCodec = packetCodec;
            this.codec = codec;
        }

        @Override
        public MapCodec<T> getCodec() {
            return this.codec;
        }

        @Override
        public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() {
            return this.packetCodec;
        }
    }
}
