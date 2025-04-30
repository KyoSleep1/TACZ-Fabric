package com.tacz.guns.crafting;

import com.mojang.serialization.MapCodec;
import com.tacz.guns.GunMod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

import java.util.List;

public class GunSmithTableSerializer implements RecipeSerializer<GunSmithTableRecipe> {
    public static final GunSmithTableSerializer INSTANCE = new GunSmithTableSerializer();

    public static final Identifier ID = Identifier.of(GunMod.MOD_ID, "gun_smith_table_crafting");
    public static final GunSmithTableRecipe EMPTY = new GunSmithTableRecipe(
            Identifier.of(GunMod.MOD_ID, "gun_crafting_empty"),
            new GunSmithTableResult(ItemStack.EMPTY, "empty"),
            List.of());

    @Override
    public MapCodec<GunSmithTableRecipe> codec() {
        return MapCodec.unit(EMPTY);
    }

    @Override
    public PacketCodec<RegistryByteBuf, GunSmithTableRecipe> packetCodec() {
        return PacketCodec.unit(EMPTY);
    }
}
