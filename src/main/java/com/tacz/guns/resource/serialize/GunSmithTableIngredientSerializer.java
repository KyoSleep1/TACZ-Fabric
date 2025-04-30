package com.tacz.guns.resource.serialize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tacz.guns.crafting.GunSmithTableIngredient;
import net.minecraft.recipe.Ingredient;

//TODO: THIS
public class GunSmithTableIngredientSerializer {
    public static final Codec<GunSmithTableIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("item").forGetter(GunSmithTableIngredient::ingredient),
            Codec.INT.optionalFieldOf("count", 1).forGetter(GunSmithTableIngredient::count)
    ).apply(instance, GunSmithTableIngredient::new));
}
