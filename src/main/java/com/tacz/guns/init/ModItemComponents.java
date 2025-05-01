package com.tacz.guns.init;

import com.mojang.serialization.Codec;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.attachment.AttachmentTypeCodec;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.api.item.gun.FireModeCodec;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItemComponents {

    private static final String GENERIC_HIDE_MASK_TAG = "hide_mask";

    private static final String GUN_ID_TAG = "gun_id";
    private static final String GUN_FIRE_MODE_TAG = "gun_firemode";
    private static final String GUN_HAS_BULLET_IN_BARREL_TAG = "has_bullet_in_barrel";
    private static final String GUN_CURRENT_AMMO_COUNT_TAG = "gun_current_ammo_count";
    private static final String GUN_EXP_TAG = "gun_level_xp";
    private static final String GUN_DUMMY_AMMO_TAG = "dummy_ammo";
    private static final String GUN_MAX_DUMMY_AMMO_TAG = "max_dummy_amo";
    private static final String GUN_ATTACHMENTS_TAG = "attachments";
    private static final String GUN_ATTACHMENT_LOCK_TAG = "attachment_lock";

    private static final String ATTACHMENT_ID_TAG = "attachment_id";
    private static final String ATTACHMENT_SKIN_ID_TAG = "attachment_skin";
    private static final String ATTACHMENT_ZOOM_NUMBER_TAG = "attachment_zoom_number";

    private static final String AMMO_BOX_ID_TAG = "ammo_box_id";
    private static final String AMMO_BOX_COUNT_TAG = "ammo_box_count";
    private static final String AMMO_BOX_CREATIVE_TAG = "ammo_box_creative";
    private static final String AMMO_BOX_ALL_TYPE_CREATIVE_TAG = "ammo_box_all_type_creative";
    private static final String AMMO_BOX_LEVEL_TAG = "ammo_box_level";

    private static final String AMMO_ID_TAG = "ammo_id";

    public static final ComponentType<Identifier> GUN_ID = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_ID_TAG),
            ComponentType.<Identifier>builder()
                    .codec(Identifier.CODEC)
                    .packetCodec(Identifier.PACKET_CODEC)
                    .build()
    );

    public static final ComponentType<FireMode> GUN_FIRE_MODE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_FIRE_MODE_TAG),
            ComponentType.<FireMode>builder()
                    .codec(FireModeCodec.CODEC)
                    .packetCodec(FireModeCodec.PACKET_CODEC)
                    .build()
    );

    public static final ComponentType<Boolean> GUN_HAS_BULLET_IN_BARREL = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_HAS_BULLET_IN_BARREL_TAG),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Integer> GUN_CURRENT_AMMO_COUNT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_CURRENT_AMMO_COUNT_TAG),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> GUN_EXP = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_EXP_TAG),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> GUN_DUMMY_AMMO = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_DUMMY_AMMO_TAG),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> GUN_MAX_DUMMY_AMMO = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_MAX_DUMMY_AMMO_TAG),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );


    public static final ComponentType<List<ItemStack>> GUN_ATTACHMENTS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_ATTACHMENTS_TAG),
            ComponentType.<List<ItemStack>>builder()
                    .codec(ItemStack.CODEC.listOf())
                    .packetCodec(ItemStack.LIST_PACKET_CODEC)
                    .build()
    );

    public static final ComponentType<Boolean> GUN_ATTACHMENT_LOCK = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GUN_ATTACHMENT_LOCK_TAG),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Identifier> ATTACHMENT_ID = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, ATTACHMENT_ID_TAG),
            ComponentType.<Identifier>builder()
                    .codec(Identifier.CODEC)
                    .packetCodec(Identifier.PACKET_CODEC)
                    .build()
    );

    public static final ComponentType<AttachmentType> ATTACHMENT_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, "attachment_type"),
            ComponentType.<AttachmentType>builder()
                    .codec(AttachmentTypeCodec.ATTACHMENT_CODEC)
                    .packetCodec(AttachmentTypeCodec.PACKET_ATTACHMENT_CODEC)
                    .build()
    );

    public static final ComponentType<Identifier> ATTACHMENT_SKIN_ID = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, ATTACHMENT_SKIN_ID_TAG),
            ComponentType.<Identifier>builder()
                    .codec(Identifier.CODEC)
                    .packetCodec(Identifier.PACKET_CODEC)
                    .build()
    );

    public static final ComponentType<Integer> ATTACHMENT_ZOOM_NUMBER = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, ATTACHMENT_ZOOM_NUMBER_TAG),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Identifier> AMMO_BOX_ID = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, AMMO_BOX_ID_TAG),
            ComponentType.<Identifier>builder()
                    .codec(Identifier.CODEC)
                    .packetCodec(Identifier.PACKET_CODEC)
                    .build()
    );

    public static final ComponentType<Integer> AMMO_BOX_COUNT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, AMMO_BOX_COUNT_TAG),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Boolean> AMMO_BOX_CREATIVE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, AMMO_BOX_CREATIVE_TAG),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Boolean> AMMO_BOX_ALL_TYPE_CREATIVE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, AMMO_BOX_ALL_TYPE_CREATIVE_TAG),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Integer> AMMO_BOX_LEVEL = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, AMMO_BOX_LEVEL_TAG),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Identifier> AMMO_ID = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, AMMO_ID_TAG),
            ComponentType.<Identifier>builder()
                    .codec(Identifier.CODEC)
                    .packetCodec(Identifier.PACKET_CODEC)
                    .build()
    );

    public static final ComponentType<Integer> GENERIC_HIDE_MASK = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(GunMod.MOD_ID, GENERIC_HIDE_MASK_TAG),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static void init() {
    }
}
