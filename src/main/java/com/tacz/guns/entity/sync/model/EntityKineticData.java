package com.tacz.guns.entity.sync.model;

import com.tacz.guns.api.DefaultAssets;
import net.minecraft.util.Identifier;

public class EntityKineticData {

    public Identifier ammoId = DefaultAssets.EMPTY_AMMO_ID;
    public final Identifier gunId;
    public float gravity = 0;
    public boolean hasExplosion = false;
    public boolean hasIgnite = false;
    public float explosionRadius = 3;
    public float explosionDamage = 3;
    public int life = 200;
    public float speed = 1;
    public float friction = 0.01F;
    public int pierce = 1;
    public boolean isTracerAmmo;

    public EntityKineticData(Identifier gunId) {
        this.gunId = gunId;
    }
}
