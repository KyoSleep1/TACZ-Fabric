package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.init.ModItemComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface AmmoBoxItemDataAccessor extends IAmmoBox {

    @Override
    default Identifier getAmmoId(ItemStack ammoBox) {
        return ammoBox.getOrDefault(ModItemComponents.AMMO_BOX_ID, DefaultAssets.EMPTY_AMMO_ID);
    }

    @Override
    default void setAmmoId(ItemStack ammoBox, Identifier ammoId) {
        ammoBox.set(ModItemComponents.AMMO_BOX_ID, ammoId);
    }

    @Override
    default int getAmmoCount(ItemStack ammoBox) {
        if (isAllTypeCreative(ammoBox) || isCreative(ammoBox)) {
            return Integer.MAX_VALUE;
        }
        return ammoBox.getOrDefault(ModItemComponents.AMMO_BOX_COUNT, 0);
    }

    @Override
    default void setAmmoCount(ItemStack ammoBox, int count) {
        if (isCreative(ammoBox)) {
            ammoBox.set(ModItemComponents.AMMO_BOX_COUNT, Integer.MAX_VALUE);
            return;
        }
        ammoBox.set(ModItemComponents.AMMO_BOX_COUNT, count);
    }

    @Override
    default boolean isAmmoBoxOfGun(ItemStack gun, ItemStack ammoBox) {
        if (gun.getItem() instanceof IGun iGun && ammoBox.getItem() instanceof IAmmoBox iAmmoBox) {
            if (isAllTypeCreative(ammoBox)) {
                return true;
            }
            Identifier ammoId = iAmmoBox.getAmmoId(ammoBox);
            if (ammoId.equals(DefaultAssets.EMPTY_AMMO_ID)) {
                return false;
            }
            Identifier gunId = iGun.getGunId(gun);
            return TimelessAPI.getCommonGunIndex(gunId).map(gunIndex -> gunIndex.getGunData().getAmmoId().equals(ammoId)).orElse(false);
        }
        return false;
    }

    @Override
    default ItemStack setAmmoLevel(ItemStack ammoBox, int level) {
        ammoBox.set(ModItemComponents.AMMO_BOX_LEVEL, Math.max(level, 0));
        return ammoBox;
    }

    @Override
    default int getAmmoLevel(ItemStack ammoBox) {
        return ammoBox.getOrDefault(ModItemComponents.AMMO_BOX_LEVEL, 0);
    }

    @Override
    default boolean isCreative(ItemStack ammoBox) {
        return ammoBox.getOrDefault(ModItemComponents.AMMO_BOX_CREATIVE, false);
    }

    @Override
    default boolean isAllTypeCreative(ItemStack ammoBox) {
        return ammoBox.getOrDefault(ModItemComponents.AMMO_BOX_ALL_TYPE_CREATIVE, false);
    }

    @Override
    default ItemStack setCreative(ItemStack ammoBox, boolean isAllType) {
        if (isAllType) {
            // 移除可能存在的创造模式标签
            if (this.isCreative(ammoBox)) {
                ammoBox.remove(ModItemComponents.AMMO_BOX_CREATIVE);
            }
            ammoBox.set(ModItemComponents.AMMO_BOX_ALL_TYPE_CREATIVE, true);
            return ammoBox;
        }
        // 移除可能存在的全类型标签
        if (this.isAllTypeCreative(ammoBox)) {
            ammoBox.remove(ModItemComponents.AMMO_BOX_ALL_TYPE_CREATIVE);
        }
        ammoBox.set(ModItemComponents.AMMO_BOX_CREATIVE, true);
        return ammoBox;
    }
}
