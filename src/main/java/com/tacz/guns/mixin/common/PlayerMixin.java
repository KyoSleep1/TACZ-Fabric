package com.tacz.guns.mixin.common;

import com.tacz.guns.event.PlayerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void port_lib$playerStartTickEvent(CallbackInfo ci) {
        PlayerTickEvents.START.invoker().onStartOfPlayerTick((PlayerEntity) (Object) this);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void port_lib$playerEndTickEvent(CallbackInfo ci) {
        PlayerTickEvents.END.invoker().onEndOfPlayerTick((PlayerEntity) (Object) this);
    }

}
