package com.tacz.guns.util.item.wrapper;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerArmorInvWrapper extends RangedWrapper {
    private final PlayerInventory inventoryPlayer;

    public PlayerArmorInvWrapper(PlayerInventory inv) {
        super(new InvWrapper(inv), inv.main.size(), inv.main.size() + inv.armor.size());
        this.inventoryPlayer = inv;
    }

    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        EquipmentSlot equ = null;
        EquipmentSlot[] var5 = EquipmentSlot.values();

        for (EquipmentSlot s : var5) {
            if (s.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && s.getEntitySlotId() == slot) {
                equ = s;
                break;
            }
        }

        return equ != null && slot < 4 && !stack.isEmpty() && canEquip(stack, equ) ? super.insertItem(slot, stack, simulate) : stack;
    }

    private boolean canEquip(ItemStack stack, EquipmentSlot armorType) {
        return this.inventoryPlayer.player.getPreferredEquipmentSlot(stack) == armorType;
    }
}
