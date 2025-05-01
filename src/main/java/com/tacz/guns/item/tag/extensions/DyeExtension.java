package com.tacz.guns.item.tag.extensions;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

public interface DyeExtension {
	default TagKey<Item> tACZ_Fabric$getTag() {
		return null;
	}
}