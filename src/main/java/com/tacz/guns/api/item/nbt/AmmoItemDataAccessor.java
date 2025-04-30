package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.init.ModItemComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AmmoItemDataAccessor extends IAmmo {

    @Override
    @NotNull
    default Identifier getAmmoId(ItemStack ammo) {
        return ammo.getOrDefault(ModItemComponents.AMMO_ID, DefaultAssets.EMPTY_AMMO_ID);
    }

    @Override
    default void setAmmoId(ItemStack ammo, @Nullable Identifier ammoId) {
        if (ammoId != null) {
            ammo.set(ModItemComponents.AMMO_ID, ammoId);
            return;
        }
        ammo.set(ModItemComponents.AMMO_ID, DefaultAssets.EMPTY_AMMO_ID);
    }

    @Override
    default boolean isAmmoOfGun(ItemStack gun, ItemStack ammo) {
        if (gun.getItem() instanceof IGun iGun && ammo.getItem() instanceof IAmmo iAmmo) {
            Identifier gunId = iGun.getGunId(gun);
            Identifier ammoId = iAmmo.getAmmoId(ammo);
            return TimelessAPI.getCommonGunIndex(gunId).map(gunIndex -> gunIndex.getGunData().getAmmoId().equals(ammoId)).orElse(false);
        }
        return false;
    }
}
