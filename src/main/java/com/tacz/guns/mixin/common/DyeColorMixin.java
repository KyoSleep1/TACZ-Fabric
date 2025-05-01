package com.tacz.guns.mixin.common;

import com.tacz.guns.item.tag.extensions.DyeExtension;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DyeColor.class)
public class DyeColorMixin implements DyeExtension {

	@Unique
	private TagKey<Item> tag;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addTag(String string, int i, int id, String name, int entityColor, MapColor mapColor, int fireworkColor,
                        int signColor, CallbackInfo ci) {
		tag = TagKey.of(Registries.ITEM.getKey(), Identifier.of("c", name + "_dyes"));
	}

	@Override
	public TagKey<Item> tACZ_Fabric$getTag() {
		return tag;
	}
}