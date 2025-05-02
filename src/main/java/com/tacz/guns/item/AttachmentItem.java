package com.tacz.guns.item;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
import com.tacz.guns.api.item.nbt.AttachmentItemDataAccessor;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.inventory.tooltip.AttachmentItemTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AttachmentItem extends Item implements AttachmentItemDataAccessor {
    public AttachmentItem() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    @NotNull
    @Environment(EnvType.CLIENT)
    public Text getName(@NotNull ItemStack stack) {
        Identifier attachmentId = this.getAttachmentId(stack);
        Optional<ClientAttachmentIndex> attachmentIndex = TimelessAPI.getClientAttachmentIndex(attachmentId);
        if (attachmentIndex.isPresent()) {
            return Text.translatable(attachmentIndex.get().getName());
        }
        return super.getName(stack);
    }

    public static DefaultedList<ItemStack> fillItemCategory(AttachmentType type) {
        DefaultedList<ItemStack> stacks = DefaultedList.of();
        TimelessAPI.getAllCommonAttachmentIndex().forEach(entry -> {
            final AttachmentType attachmentType = entry.getValue().getType();
            if (type.equals(attachmentType)) {
                ItemStack itemStack = AttachmentItemBuilder.create()
                        .setId(entry.getKey())
                        .build(attachmentType);
                stacks.add(itemStack);
            }
        });
        return stacks;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return Optional.of(new AttachmentItemTooltip(this.getAttachmentId(stack),
                this.getAttachmentType(stack)));
    }
}
