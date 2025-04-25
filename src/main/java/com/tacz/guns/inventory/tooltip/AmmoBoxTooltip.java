package com.tacz.guns.inventory.tooltip;

import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;

public record AmmoBoxTooltip(ItemStack ammoBox, ItemStack ammo, int count) implements TooltipData {
}
