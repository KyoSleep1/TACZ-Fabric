package com.tacz.guns.client.init;

import com.tacz.guns.api.client.other.ThirdPersonManager;
import com.tacz.guns.client.input.*;
import com.tacz.guns.client.tooltip.ClientAmmoBoxTooltip;
import com.tacz.guns.client.tooltip.ClientAttachmentItemTooltip;
import com.tacz.guns.client.tooltip.ClientGunTooltip;
import com.tacz.guns.compat.immediatelyfast.ImmediatelyFastCompat;
import com.tacz.guns.init.ModItems;
import com.tacz.guns.inventory.tooltip.AmmoBoxTooltip;
import com.tacz.guns.inventory.tooltip.AttachmentItemTooltip;
import com.tacz.guns.inventory.tooltip.GunTooltip;
import com.tacz.guns.item.AmmoBoxItem;
import com.tacz.guns.mixin.client.MinecraftClientAccessor;
import committee.nova.mkb.api.IKeyBinding;
import committee.nova.mkb.keybinding.KeyConflictContext;
import committee.nova.mkb.keybinding.KeyModifier;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;

public class ClientSetup {

    private static void keybindingRegister() {
        final KeyBinding[] keys = {
                InspectKey.INSPECT_KEY,
                ReloadKey.RELOAD_KEY,
                ShootKey.SHOOT_KEY,
                InteractKey.INTERACT_KEY,
                FireSelectKey.FIRE_SELECT_KEY,
                AimKey.AIM_KEY,
                RefitKey.REFIT_KEY,
                ZoomKey.ZOOM_KEY,
                MeleeKey.MELEE_KEY,
                ConfigKey.OPEN_CONFIG_KEY
        };

        var configKey = ((IKeyBinding)ConfigKey.OPEN_CONFIG_KEY);
        try {
            var field = configKey.getClass().getDeclaredField("keyModifierDefault");
            field.setAccessible(true);
            field.set(configKey, KeyModifier.ALT);
            field.setAccessible(false);
        } catch (Exception ignored) {}

        for (KeyBinding key : keys) {
            IKeyBinding ikb = (IKeyBinding) key;
            ikb.setKeyConflictContext(KeyConflictContext.IN_GAME);
            KeyBindingHelper.registerKeyBinding(key);
        }
    }

    private static TooltipComponent tooltipComponent(net.minecraft.item.tooltip.TooltipData tooltip) {
        if (tooltip instanceof GunTooltip gunTooltip) {
            return new ClientGunTooltip(gunTooltip);
        }
        if (tooltip instanceof AmmoBoxTooltip ammoBoxTooltip) {
            return new ClientAmmoBoxTooltip(ammoBoxTooltip);
        }
        if (tooltip instanceof AttachmentItemTooltip attachmentItemTooltip) {
            return new ClientAttachmentItemTooltip(attachmentItemTooltip);
        }
        return null;
    }

    public static void onClientSetup() {
        keybindingRegister();
        TooltipComponentCallback.EVENT.register(ClientSetup::tooltipComponent);

        // 注册自己的的硬编码第三人称动画
        ThirdPersonManager.registerDefault();

        // 注册颜色
        MinecraftClient.getInstance().execute(() ->
                ((MinecraftClientAccessor)MinecraftClient.getInstance()).getItemColors().register(AmmoBoxItem::getColor, ModItems.AMMO_BOX));

        // 注册变种
        ModelPredicateProviderRegistry.register(ModItems.AMMO_BOX, AmmoBoxItem.PROPERTY_NAME, AmmoBoxItem::getStatue);

        // 初始化自己的枪包下载器
        ImmediatelyFastCompat.init();
    }
}
