package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.server.AmmoHitBlockEvent;
import com.tacz.guns.event.*;
import com.tacz.guns.event.ammo.BellRing;
import com.tacz.guns.event.ammo.DestroyGlassBlock;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class ModEvents {

    public static void init() {
        ServerPlayConnectionEvents.JOIN.register(EnterServerEvent::onLoggedInServer);

        PlayerTickEvents.END.register(HitboxHelperEvent::onPlayerTick);
        ServerPlayConnectionEvents.DISCONNECT.register(HitboxHelperEvent::onPlayDisconnect);

        LivingEntityEvents.KNOCKBACK_STRENGTH.register(KnockbackChange::onKnockback);

        NeoForgeModConfigEvents.loading(GunMod.MOD_ID).register(LoadingConfigEvent::onModConfigLoading);
        NeoForgeModConfigEvents.reloading(GunMod.MOD_ID).register(LoadingConfigEvent::onModConfigReloading);

        ServerPlayerEvents.AFTER_RESPAWN.register(PlayerRespawnEvent::afterRespawn);

        AttackBlockCallback.EVENT.register(PreventGunClick::onLeftClickBlock);

        ServerTickEvents.START_SERVER_TICK.register(ServerTickEvent::onServerTick);
        ServerTickEvents.END_SERVER_TICK.register(ServerTickEvent::onServerTick);

        // ammo
        AmmoHitBlockEvent.EVENT.register(BellRing::onAmmoHitBlock);

        AmmoHitBlockEvent.EVENT.register(DestroyGlassBlock::onAmmoHitBlock);
    }
}
