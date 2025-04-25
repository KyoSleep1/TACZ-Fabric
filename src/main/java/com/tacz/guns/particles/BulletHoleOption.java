package com.tacz.guns.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tacz.guns.init.ModParticles;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BulletHoleOption implements ParticleEffect {
    public static final MapCodec<BulletHoleOption> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("dir").forGetter(option -> option.direction.ordinal()),
                    Codec.LONG.fieldOf("pos").forGetter(option -> option.pos.asLong()),
                    Codec.STRING.fieldOf("ammo_id").forGetter(option -> option.ammoId),
                    Codec.STRING.fieldOf("gun_id").forGetter(option -> option.gunId)
            ).apply(instance, BulletHoleOption::new));

    public static final PacketCodec<? super RegistryByteBuf, BulletHoleOption> DESERIALIZER = new PacketCodec<>() {
        @Override
        public BulletHoleOption decode(RegistryByteBuf buf) {
            int dir = buf.readInt();
            long pos = buf.readLong();
            String ammoId = buf.readString();
            String gunId = buf.readString();
            return new BulletHoleOption(dir, pos, ammoId, gunId);
        }

        @Override
        public void encode(RegistryByteBuf buf, BulletHoleOption value) {
            buf.writeInt(value.direction.ordinal());
            buf.writeLong(value.pos.asLong());
            buf.writeString(value.ammoId);
            buf.writeString(value.gunId);
        }
    };

    private final Direction direction;
    private final BlockPos pos;
    private final String ammoId;
    private final String gunId;

    public BulletHoleOption(int dir, long pos, String ammoId, String gunId) {
        this.direction = Direction.values()[dir];
        this.pos = BlockPos.fromLong(pos);
        this.ammoId = ammoId;
        this.gunId = gunId;
    }

    public BulletHoleOption(Direction dir, BlockPos pos, String ammoId, String gunId) {
        this.direction = dir;
        this.pos = pos;
        this.ammoId = ammoId;
        this.gunId = gunId;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public String getAmmoId() {
        return ammoId;
    }

    public String getGunId() {
        return gunId;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.BULLET_HOLE;
    }
}
