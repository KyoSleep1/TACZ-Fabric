package com.tacz.guns.api.item.gun;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;

public class FireModeCodec {

    public static final Codec<FireMode> CODEC = Codec.STRING
            .comapFlatMap(FireModeCodec::validate, FireMode::toString)
            .stable();

    public static final PacketCodec<PacketByteBuf, FireMode> PACKET_CODEC =
            PacketCodec.of(
                    (value, buf) -> buf.writeEnumConstant(value),
                    buf -> buf.readEnumConstant(FireMode.class));

    public static DataResult<FireMode> validate(String id) {
        try {
            return DataResult.success(FireMode.valueOf(id));
        } catch (Exception exception) {
            return DataResult.error(() -> "Invalid FireMode: " + id);
        }
    }
}
