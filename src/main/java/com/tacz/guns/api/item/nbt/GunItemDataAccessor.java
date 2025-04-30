package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.init.ModItemComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface GunItemDataAccessor extends IGun {

    @Override
    default boolean useDummyAmmo(ItemStack gun) {
        return gun.contains(ModItemComponents.GUN_DUMMY_AMMO);
    }

    @Override
    @SuppressWarnings("all")
    default int getDummyAmmoAmount(ItemStack gun) {
        if (!this.useDummyAmmo(gun)) {
            return 0;
        }
        return Math.max(0, gun.get(ModItemComponents.GUN_DUMMY_AMMO));
    }

    @Override
    default void setDummyAmmoAmount(ItemStack gun, int amount) {
        gun.set(ModItemComponents.GUN_DUMMY_AMMO, Math.max(amount, 0));
    }

    @Override
    default void addDummyAmmoAmount(ItemStack gun, int amount) {
        if (!useDummyAmmo(gun)) {
            return;
        }
        if (!hasMaxDummyAmmo(gun)) {
            return;
        }
        amount = Math.min(getDummyAmmoAmount(gun) + amount, getMaxDummyAmmoAmount(gun));
        this.setDummyAmmoAmount(gun, Math.max(amount, 0));
    }

    @Override
    default boolean hasMaxDummyAmmo(ItemStack gun) {
        return gun.contains(ModItemComponents.GUN_MAX_DUMMY_AMMO);
    }

    @Override
    @SuppressWarnings("all")
    default int getMaxDummyAmmoAmount(ItemStack gun) {
        if (!this.hasMaxDummyAmmo(gun)) {
            return 0;
        }
        return Math.max(0, gun.get(ModItemComponents.GUN_MAX_DUMMY_AMMO));
    }

    @Override
    default void setMaxDummyAmmoAmount(ItemStack gun, int amount) {
        gun.set(ModItemComponents.GUN_MAX_DUMMY_AMMO, Math.max(amount, 0));
    }

    @Override
    default boolean hasAttachmentLock(ItemStack gun) {
        return gun.getOrDefault(ModItemComponents.GUN_ATTACHMENT_LOCK, false);
    }

    @Override
    default void setAttachmentLock(ItemStack gun, boolean lock) {
        gun.set(ModItemComponents.GUN_ATTACHMENT_LOCK, lock);
    }

    @Override
    @NotNull
    default Identifier getGunId(ItemStack gun) {
        return gun.getOrDefault(ModItemComponents.GUN_ID, DefaultAssets.EMPTY_GUN_ID);
    }

    @Override
    default void setGunId(ItemStack gun, @Nullable Identifier gunId) {
        if (gunId != null) {
            gun.set(ModItemComponents.GUN_ID, gunId);
        }
    }

    @Override
    default int getLevel(ItemStack gun) {
        int exp = getExp(gun);
        return getLevel(exp);
    }

    @Override
    default int getExp(ItemStack gun) {
        return gun.getOrDefault(ModItemComponents.GUN_EXP, 0);
    }

    @Override
    default int getExpToNextLevel(ItemStack gun) {
        int exp = getExp(gun);
        int level = getLevel(exp);
        if (level >= getMaxLevel()) {
            return 0;
        }
        int nextLevelExp = getExp(level + 1);
        return nextLevelExp - exp;
    }

    @Override
    default int getExpCurrentLevel(ItemStack gun) {
        int exp = getExp(gun);
        int level = getLevel(exp);
        if (level <= 0) {
            return exp;
        } else {
            return exp - getExp(level - 1);
        }
    }

    @Override
    default FireMode getFireMode(ItemStack gun) {
        return gun.getOrDefault(ModItemComponents.GUN_FIRE_MODE, FireMode.UNKNOWN);
    }

    @Override
    default void setFireMode(ItemStack gun, @Nullable FireMode fireMode) {
        gun.set(ModItemComponents.GUN_FIRE_MODE, Objects.requireNonNullElse(fireMode, FireMode.UNKNOWN));
    }

    @Override
    default int getCurrentAmmoCount(ItemStack gun) {
        return gun.getOrDefault(ModItemComponents.GUN_CURRENT_AMMO_COUNT, 0);
    }

    @Override
    default void setCurrentAmmoCount(ItemStack gun, int ammoCount) {
        gun.set(ModItemComponents.GUN_CURRENT_AMMO_COUNT, Math.max(ammoCount, 0));
    }

    @Override
    default void reduceCurrentAmmoCount(ItemStack gun) {
        setCurrentAmmoCount(gun, getCurrentAmmoCount(gun) - 1);
    }

    @Override
    default ItemStack getAttachment(ItemStack gun, AttachmentType type) {
        if (!this.allowAttachmentType(gun, type)) return ItemStack.EMPTY;

        final List<ItemStack> attachments = gun.get(ModItemComponents.GUN_ATTACHMENTS);
        if (attachments == null) return ItemStack.EMPTY;

        for (ItemStack attachment : attachments) {
            final AttachmentType componentType = attachment.getOrDefault(ModItemComponents.ATTACHMENT_TYPE,
                    null);
            if (componentType == null) continue;
            if (componentType == type) return attachment;
        }

        return ItemStack.EMPTY;
    }

    @Override
    @NotNull
    default Identifier getAttachmentId(ItemStack gun, AttachmentType type) {
        final ItemStack stack = this.getAttachment(gun, type);
        if (stack.isEmpty()) {
            return DefaultAssets.EMPTY_ATTACHMENT_ID;
        }
        return stack.getOrDefault(ModItemComponents.ATTACHMENT_ID, DefaultAssets.EMPTY_ATTACHMENT_ID);
    }

    @Override
    default void installAttachment(@NotNull ItemStack gun, @NotNull ItemStack attachment) {
        if (!allowAttachment(gun, attachment)) {
            return;
        }
        final IAttachment iAttachment = IAttachment.getIAttachmentOrNull(attachment);
        if (iAttachment == null) {
            return;
        }
        final List<ItemStack> attachments = gun.getOrDefault(ModItemComponents.GUN_ATTACHMENTS, new ArrayList<>());
        attachments.add(attachment);
        gun.set(ModItemComponents.GUN_ATTACHMENTS, attachments);
    }

    @Override
    default void unloadAttachment(@NotNull ItemStack gun, AttachmentType type) {
        if (!allowAttachmentType(gun, type)) {
            return;
        }
        final List<ItemStack> attachments = gun.getOrDefault(ModItemComponents.GUN_ATTACHMENTS, new ArrayList<>());
        final ItemStack attachment = this.getAttachment(gun, type);
        if (attachment.isEmpty()) return;
        attachments.remove(attachment);
        gun.set(ModItemComponents.GUN_ATTACHMENTS, attachments);
    }

    @Override
    default float getAimingZoom(ItemStack gunItem) {
        float zoom = 1;
        Identifier scopeId = this.getAttachmentId(gunItem, AttachmentType.SCOPE);
        if (!DefaultAssets.isEmptyAttachmentId(scopeId)) {
            final ItemStack stack = this.getAttachment(gunItem, AttachmentType.SCOPE);
            int zoomNumber = AttachmentItemDataAccessor.getZoomNumber(stack);
            float[] zooms = TimelessAPI.getClientAttachmentIndex(scopeId).map(ClientAttachmentIndex::getZoom).orElse(null);
            if (zooms != null) {
                zoom = zooms[zoomNumber % zooms.length];
            }
        } else {
            Identifier gunId = this.getGunId(gunItem);
            zoom = TimelessAPI.getClientGunIndex(gunId).map(ClientGunIndex::getIronZoom).orElse(1f);
        }
        return zoom;
    }

    @Override
    default boolean hasBulletInBarrel(ItemStack gun) {
        return gun.getOrDefault(ModItemComponents.GUN_HAS_BULLET_IN_BARREL, false);
    }

    @Override
    default void setBulletInBarrel(ItemStack gun, boolean bulletInBarrel) {
        gun.set(ModItemComponents.GUN_HAS_BULLET_IN_BARREL, bulletInBarrel);
    }
}
