package com.tacz.guns.network;

import com.sollace.fabwork.api.packets.S2CPacketType;
import com.sollace.fabwork.api.packets.SimpleNetworking;
import com.tacz.guns.GunMod;
import com.tacz.guns.network.packets.s2c.*;
import com.tacz.guns.network.packets.s2c.event.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings("deprecation")
public class NetworkClientHandler {

    public static final S2CPacketType<SoundS2CPacket> SOUND = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "sound"), SoundS2CPacket::new);
    public static final S2CPacketType<CraftS2CPacket> CRAFT = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "craft"), CraftS2CPacket::new);
    public static final S2CPacketType<RefreshRefitScreenS2CPacket> REFRESH_REFIT_SCREEN = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "refresh_refit_screen"), RefreshRefitScreenS2CPacket::new);
    public static final S2CPacketType<SwapItemS2CPacket> SWAP_ITEM = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "swap_item"), SwapItemS2CPacket::new);
    public static final S2CPacketType<LevelUpS2CPacket> LEVEL_UP = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "level_up"), LevelUpS2CPacket::new);
    public static final S2CPacketType<SyncGunPackS2CPacket> SYNC_GUN_PACK = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "sync_gun_pack"), SyncGunPackS2CPacket::new);
    public static final S2CPacketType<GunHurtS2CPacket> GUN_HURT = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "gun_hurt"), GunHurtS2CPacket::new);
    public static final S2CPacketType<GunKillS2CPacket> GUN_KILL = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "gun_kill"), GunKillS2CPacket::new);
    public static final S2CPacketType<GunDrawS2CPacket> GUN_DRAW = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "gun_draw"), GunDrawS2CPacket::new);
    public static final S2CPacketType<GunFireS2CPacket> GUN_FIRE = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "gun_fire"), GunFireS2CPacket::new);
    public static final S2CPacketType<GunFireSelectS2CPacket> GUN_FIRE_SELECT = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "gun_fire_select"), GunFireSelectS2CPacket::new);
    public static final S2CPacketType<GunMeleeS2CPacket> GUN_MELEE = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "gun_melee"), GunMeleeS2CPacket::new);
    public static final S2CPacketType<GunReloadS2CPacket> GUN_RELOAD = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "gun_reload"), GunReloadS2CPacket::new);
    public static final S2CPacketType<GunShootS2CPacket> GUN_SHOOT = SimpleNetworking
            .serverToClient(Identifier.of(GunMod.MOD_ID, "gun_shoot"), GunShootS2CPacket::new);

    @Environment(EnvType.CLIENT)
    public static final void init() {
    }
}
