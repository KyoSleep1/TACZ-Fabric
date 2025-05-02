package com.tacz.guns.entity.sync;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.Vec3d;

public class Vec3DSync {

    public static final PacketCodec<RegistryByteBuf, Vec3d> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public Vec3d decode(RegistryByteBuf buf) {
            return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        }

        @Override
        public void encode(RegistryByteBuf buf, Vec3d value) {
            buf.writeDouble(value.getX());
            buf.writeDouble(value.getY());
            buf.writeDouble(value.getZ());
        }
    };
    public static final TrackedDataHandler<Vec3d> VEC3D = TrackedDataHandler.create(PACKET_CODEC);

    public static void init() {
        TrackedDataHandlerRegistry.register(VEC3D);
    }
}
