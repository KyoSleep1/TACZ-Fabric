package com.tacz.guns.network.packets.s2c;

import com.sollace.fabwork.api.packets.HandledPacket;
import com.tacz.guns.client.sound.SoundPlayManager;
import com.tacz.guns.util.EnvironmentUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SoundS2CPacket implements HandledPacket<PlayerEntity> {

    private final int entityId;
    private final Identifier gunId;
    private final String soundName;
    private final float volume;
    private final float pitch;
    private final int distance;

    public SoundS2CPacket(PacketByteBuf buf) {
        this(buf.readVarInt(), buf.readIdentifier(), buf.readString(), buf.readFloat(), buf.readFloat(), buf.readInt());
    }

    public SoundS2CPacket(int entityId, Identifier gunId, String soundName, float volume, float pitch, int distance) {
        this.entityId = entityId;
        this.gunId = gunId;
        this.soundName = soundName;
        this.volume = volume;
        this.pitch = pitch;
        this.distance = distance;
    }

    @Override
    public void toBuffer(PacketByteBuf buf) {
        buf.writeVarInt(entityId);
        buf.writeIdentifier(gunId);
        buf.writeString(soundName);
        buf.writeFloat(volume);
        buf.writeFloat(pitch);
        buf.writeInt(distance);
    }

    @Override
    public void handle(PlayerEntity ignoredPlayer) {
        if (EnvironmentUtil.isClient()) {
            SoundPlayManager.playMessageSound(this);
        }
    }

    public int getEntityId() {
        return entityId;
    }

    public Identifier getGunId() {
        return gunId;
    }

    public String getSoundName() {
        return soundName;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public int getDistance() {
        return distance;
    }
}
