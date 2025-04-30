package com.tacz.guns.client.resource.serialize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;

//TODO: THIS
public class ItemStackSerializer {
    public static final Codec<ItemStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("item").flatXmap(
                    id -> {
                        Item item = Registries.ITEM.get(id);
                        return DataResult.success(item);
                    },
                    item -> DataResult.success(Registries.ITEM.getId(item))
            ).forGetter(ItemStack::getItem),
            Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStack::getCount),
            NbtCompound.CODEC.optionalFieldOf("nbt").forGetter(stack -> {
                NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
                return Optional.ofNullable(nbtComponent != null ? nbtComponent.copyNbt() : null);
            })
    ).apply(instance, (item, count, nbtOpt) -> {
        ItemStack stack = new ItemStack(item, count);
        nbtOpt.ifPresent(nbt -> stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt)));
        return stack;
    }));
}
