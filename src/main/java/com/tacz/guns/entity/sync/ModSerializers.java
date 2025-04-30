package com.tacz.guns.entity.sync;

import com.mrcrayfish.framework.api.sync.DataSerializer;
import com.tacz.guns.api.entity.ReloadState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;

public class ModSerializers {

    public static final PacketCodec<PacketByteBuf, ReloadState> RELOAD_STATE_CODEC = new PacketCodec<>() {

        @Override
        public void encode(PacketByteBuf buf, ReloadState value) {
            buf.writeInt(value.getStateType().ordinal());
            buf.writeLong(value.getCountDown());
        }

        @Override
        public ReloadState decode(PacketByteBuf buf) {
            ReloadState reloadState = new ReloadState();
            reloadState.setStateType(ReloadState.StateType.values()[buf.readInt()]);
            reloadState.setCountDown(buf.readLong());
            return reloadState;
        }
    };

    public static final DataSerializer<ReloadState> RELOAD_STATE = new DataSerializer<>(RELOAD_STATE_CODEC,
            (val, provider) -> {
                NbtCompound compound = new NbtCompound();
                compound.putString("StateType", val.getStateType().toString());
                compound.putLong("CountDown", val.getCountDown());
                return compound;
            },
            (tag, provider) -> {
                NbtCompound compound = (NbtCompound) tag;
                try {
                    ReloadState.StateType stateType = ReloadState.StateType.valueOf(compound.getString("StateType"));
                    long countDown = compound.getLong("CountDown");
                    ReloadState reloadState = new ReloadState();
                    reloadState.setStateType(stateType);
                    reloadState.setCountDown(countDown);
                    return reloadState;
                } catch (IllegalArgumentException e) {
                    return new ReloadState(); // Return default if deserialization fails
                }
            }
    );
}
