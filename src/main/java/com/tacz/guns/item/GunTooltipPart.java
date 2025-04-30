package com.tacz.guns.item;

import com.tacz.guns.init.ModItemComponents;
import net.minecraft.item.ItemStack;

public enum GunTooltipPart {
    DESCRIPTION,
    AMMO_INFO,
    BASE_INFO,
    EXTRA_DAMAGE_INFO,
    UPGRADES_TIP,
    PACK_INFO;

    private final int mask = 1 << this.ordinal();

    public int getMask() {
        return this.mask;
    }

    public static int getHideFlags(ItemStack stack) {
        return stack.getOrDefault(ModItemComponents.GENERIC_HIDE_MASK, 0);
    }

    public static void setHideFlags(ItemStack stack, int mask) {
        stack.set(ModItemComponents.GENERIC_HIDE_MASK, mask);
    }
}
