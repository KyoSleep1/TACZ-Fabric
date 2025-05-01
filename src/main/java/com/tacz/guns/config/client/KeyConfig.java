package com.tacz.guns.config.client;

import net.neoforged.neoforge.common.ModConfigSpec;

public class KeyConfig {
    public static ModConfigSpec.BooleanValue HOLD_TO_AIM;

    public static void init(ModConfigSpec.Builder builder) {
        builder.push("key");

        builder.comment("True if you want to hold the right mouse button to aim");
        HOLD_TO_AIM = builder.define("HoldToAim", true);

        builder.pop();
    }
}
