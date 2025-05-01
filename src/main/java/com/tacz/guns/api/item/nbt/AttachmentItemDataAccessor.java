package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.init.ModItemComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AttachmentItemDataAccessor extends IAttachment {

    static int getZoomNumber(ItemStack attachment) {
        return attachment.getOrDefault(ModItemComponents.ATTACHMENT_ZOOM_NUMBER, 0);
    }

    static void setZoomNumber(ItemStack attachment, int zoomNumber) {
        attachment.set(ModItemComponents.ATTACHMENT_ZOOM_NUMBER, zoomNumber);
    }

    @Override
    @NotNull
    default Identifier getAttachmentId(ItemStack attachment) {
        return attachment.getOrDefault(ModItemComponents.ATTACHMENT_ID, DefaultAssets.EMPTY_ATTACHMENT_ID);
    }

    @Override
    default void setAttachmentId(ItemStack attachment, @Nullable Identifier attachmentId) {
        attachment.set(ModItemComponents.ATTACHMENT_ID, attachmentId);
    }

    @Override
    @Nullable
    default Identifier getSkinId(ItemStack attachment) {
        return attachment.getOrDefault(ModItemComponents.ATTACHMENT_SKIN_ID, null);
    }

    @Override
    default void setSkinId(ItemStack attachment, @Nullable Identifier skinId) {
        if (skinId != null) {
            attachment.set(ModItemComponents.ATTACHMENT_SKIN_ID, skinId);
        } else {
            attachment.remove(ModItemComponents.ATTACHMENT_SKIN_ID);
        }
    }

    @Override
    default int internalGetZoomNumber(ItemStack attachment) {
        return AttachmentItemDataAccessor.getZoomNumber(attachment);
    }

    @Override
    default void internalSetZoomNumber(ItemStack attachment, int zoomNumber) {
        AttachmentItemDataAccessor.setZoomNumber(attachment, zoomNumber);
    }
}
