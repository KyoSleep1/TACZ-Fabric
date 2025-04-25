package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.server.AmmoHitBlockEvent;
import com.tacz.guns.event.*;
import com.tacz.guns.event.ammo.BellRing;
import com.tacz.guns.event.ammo.DestroyGlassBlock;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class ModEvents {

    public static void init() {
        ServerPlayConnectionEvents.JOIN.register(EnterServerEvent::onLoggedInServer);

        PlayerTickEvents.END.register(HitboxHelperEvent::onPlayerTick);
        ServerPlayConnectionEvents.DISCONNECT.register(HitboxHelperEvent::onPlayDisconnect);

        LivingEntityEvents.KNOCKBACK_STRENGTH.register(KnockbackChange::onKnockback);

        ModConfigEvents.loading(GunMod.MOD_ID).register(LoadingConfigEvent::onModConfigLoading);
        ModConfigEvents.reloading(GunMod.MOD_ID).register(LoadingConfigEvent::onModConfigReloading);

        ServerPlayerEvents.AFTER_RESPAWN.register(PlayerRespawnEvent::afterRespawn);

        AttackBlockCallback.EVENT.register(PreventGunClick::onLeftClickBlock);

        ServerTickEvents.START_SERVER_TICK.register(ServerTickEvent::onServerTick);
        ServerTickEvents.END_SERVER_TICK.register(ServerTickEvent::onServerTick);

        EntityTrackingEvents.START_TRACKING.register(SyncedEntityDataEvent::onStartTracking);
        ServerEntityEvents.ENTITY_LOAD.register(SyncedEntityDataEvent::onPlayerJoinWorld);
        ServerPlayerEvents.COPY_FROM.register(SyncedEntityDataEvent::copyFromPlayer);
        ServerTickEvents.START_SERVER_TICK.register(SyncedEntityDataEvent::onServerTick);

        // ammo
        AmmoHitBlockEvent.EVENT.register(BellRing::onAmmoHitBlock);

        AmmoHitBlockEvent.EVENT.register(DestroyGlassBlock::onAmmoHitBlock);
    }
}
