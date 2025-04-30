package com.tacz.guns.api.item.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;


public class AttachmentTypeCodec {

    public static final Codec<AttachmentType> ATTACHMENT_CODEC = Codec.STRING
            .comapFlatMap(AttachmentTypeCodec::validate, AttachmentType::toString)
            .stable();

    public static final PacketCodec<PacketByteBuf, AttachmentType> PACKET_ATTACHMENT_CODEC = PacketCodec.of(
            (value, buf)
                    -> buf.writeEnumConstant(value),
            buf -> buf.readEnumConstant(AttachmentType.class)
    );

    private static DataResult<AttachmentType> validate(String id) {
        try {
            return DataResult.success(AttachmentType.valueOf(id));
        } catch (Exception exception) {
            return DataResult.error(() -> "Invalid AttachmentType: " + id);
        }
    }
}
