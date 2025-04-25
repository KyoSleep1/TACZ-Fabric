package com.tacz.guns.network;

import com.sollace.fabwork.api.packets.C2SPacketType;
import com.sollace.fabwork.api.packets.SimpleNetworking;
import com.tacz.guns.GunMod;
import com.tacz.guns.network.packets.c2s.*;
import com.tacz.guns.util.EnvironmentUtil;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class NetworkHandler {

    public static C2SPacketType<PlayerShootC2SPacket> PLAYER_SHOOT = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_shoot"), PlayerShootC2SPacket::new);
    public static C2SPacketType<PlayerReloadGunC2SPacket> PLAYER_RELOAD = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_reload"), PlayerReloadGunC2SPacket::new);
    public static C2SPacketType<PlayerFireSelectC2SPacket> PLAYER_FIRE_SELECT = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_fire_select"), PlayerFireSelectC2SPacket::new);
    public static C2SPacketType<PlayerAimC2SPacket> PLAYER_AIM = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_aim"), PlayerAimC2SPacket::new);
    public static C2SPacketType<PlayerDrawGunC2SPacket> PLAYER_DRAW = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_draw"), PlayerDrawGunC2SPacket::new);
    public static C2SPacketType<CraftC2SPacket> CRAFT = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "craft"), CraftC2SPacket::new);
    public static C2SPacketType<PlayerZoomC2SPacket> PLAYER_ZOOM = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_zoom"), PlayerZoomC2SPacket::new);
    public static C2SPacketType<RefitGunC2SPacket> REFIT_GUN = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "refit_gun"), RefitGunC2SPacket::new);
    public static C2SPacketType<PlayerBoltGunC2SPacket> PLAYER_BOLT = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_bolt"), PlayerBoltGunC2SPacket::new);
    public static C2SPacketType<UnloadAttachmentC2SPacket> PLAYER_UNLOAD_ATTACHMENT = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_unload_attachment"), UnloadAttachmentC2SPacket::new);
    public static C2SPacketType<PlayerMeleeC2SPacket> PLAYER_MELEE = SimpleNetworking
            .clientToServer(Identifier.of(GunMod.MOD_ID, "player_melee"), PlayerMeleeC2SPacket::new);

    public static void init() {
        if (EnvironmentUtil.isClient()) {
            NetworkClientHandler.init();
        }
    }
}
