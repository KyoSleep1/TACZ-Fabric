package com.tacz.guns.entity;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.LogicalSide;
import com.tacz.guns.api.entity.ITargetEntity;
import com.tacz.guns.api.entity.KnockBackModifier;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.api.event.common.EntityKillByGunEvent;
import com.tacz.guns.api.event.server.AmmoHitBlockEvent;
import com.tacz.guns.client.particle.AmmoParticleSpawner;
import com.tacz.guns.config.common.AmmoConfig;
import com.tacz.guns.config.sync.SyncConfig;
import com.tacz.guns.config.util.HeadShotAABBConfigRead;
import com.tacz.guns.entity.sync.Vec3DSync;
import com.tacz.guns.init.ModDamageTypes;
import com.tacz.guns.network.NetworkClientHandler;
import com.tacz.guns.network.packets.s2c.event.GunHurtS2CPacket;
import com.tacz.guns.network.packets.s2c.event.GunKillS2CPacket;
import com.tacz.guns.particles.BulletHoleOption;
import com.tacz.guns.resource.pojo.data.gun.BulletData;
import com.tacz.guns.resource.pojo.data.gun.ExplosionData;
import com.tacz.guns.resource.pojo.data.gun.ExtraDamage;
import com.tacz.guns.util.HitboxHelper;
import com.tacz.guns.util.TacHitResult;
import com.tacz.guns.util.block.BlockRayTrace;
import com.tacz.guns.util.block.ProjectileExplosion;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

/**
 * 动能武器打出的子弹实体。
 */
public class EntityKineticBullet extends ProjectileEntity {
    public static final EntityType<EntityKineticBullet> TYPE = EntityType.Builder.<EntityKineticBullet>create(EntityKineticBullet::new,
                    SpawnGroup.MISC)
            .disableSummon()
            .disableSaving()
            .makeFireImmune()
            .dimensions(0.0625F, 0.0625F)
            .maxTrackingRange(5)
            .trackingTickInterval(5)
            .build("bullet");
    private static final Predicate<Entity> PROJECTILE_TARGETS = input -> input != null && input.canHit() && !input.isSpectator();

    public static final TrackedData<String> gunId = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.STRING);
    public static final TrackedData<String> ammoId = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.STRING);
    public static final TrackedData<Float> gravity = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Boolean> hasExplosion = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> hasIgnite = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Float> explosionRadius = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> explosionDamage = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Integer> life = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Float> speed = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> friction = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Integer> pierce = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> isTracerAmmo = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Float> pitch = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> yaw = DataTracker.registerData(EntityKineticBullet.class,
            TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Vec3d> velocity = DataTracker.registerData(EntityKineticBullet.class,
            Vec3DSync.VEC3D);

    private Identifier tempGunId;
    private Identifier tempAmmoId;
    private float damageAmount = 5;
    private float knockback = 0;
    private int igniteEntityTime = 2;
    private int explosionDelayCount = Integer.MAX_VALUE;
    private boolean explosionKnockback = false;
    private ExtraDamage extraDamage = null;
    private float damageModifier = 1;
    private Vec3d startPos;
    private Vec3d originCameraPosition;
    private Vec3d originRenderOffset;

    public EntityKineticBullet(EntityType<? extends ProjectileEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityKineticBullet(EntityType<? extends ProjectileEntity> type, double x, double y, double z, World worldIn) {
        this(type, worldIn);
        this.setPosition(x, y, z);
    }

    public EntityKineticBullet(World worldIn, LivingEntity throwerIn, Identifier ammoId, Identifier gunId, boolean isTracerAmmo, BulletData data) {
        this(TYPE, throwerIn.getX(), throwerIn.getEyeY() - (double) 0.1F, throwerIn.getZ(), worldIn);
        this.initKineticData(worldIn, throwerIn, ammoId, gunId, isTracerAmmo, data);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(ammoId, DefaultAssets.EMPTY_AMMO_ID.toString());
        builder.add(gunId, DefaultAssets.EMPTY_GUN_ID.toString());
        builder.add(gravity, 0.0F);
        builder.add(hasExplosion, false);
        builder.add(hasIgnite, false);
        builder.add(explosionRadius, 3.0F);
        builder.add(explosionDamage, 3.0F);
        builder.add(life, 200);
        builder.add(speed, 1.0F);
        builder.add(friction, 0.01F);
        builder.add(pierce, 1);
        builder.add(isTracerAmmo, false);
        builder.add(velocity, this.getVelocity());
        builder.add(yaw, this.getYaw());
        builder.add(pitch, this.getPitch());
    }

    private void initKineticData(World world, LivingEntity thrower, Identifier ammoId, Identifier gunId, boolean isTracerAmmo, BulletData data) {
        this.setOwner(thrower);
        this.dataTracker.set(EntityKineticBullet.gunId, gunId.toString());
        this.dataTracker.set(EntityKineticBullet.ammoId, ammoId.toString());
        this.dataTracker.set(EntityKineticBullet.life, MathHelper.clamp((int) (data.getLifeSecond() * 20), 1, Integer.MAX_VALUE));

        // 限制最大弹速为 600 m / s，以减轻计算负担
        this.dataTracker.set(EntityKineticBullet.speed, MathHelper.clamp(data.getSpeed() / 20, 0, 30));
        this.dataTracker.set(EntityKineticBullet.gravity, MathHelper.clamp(data.getGravity(), 0, Float.MAX_VALUE));
        this.dataTracker.set(EntityKineticBullet.friction, MathHelper.clamp(data.getFriction(), 0, Float.MAX_VALUE));
        this.dataTracker.set(EntityKineticBullet.hasIgnite, data.isHasIgnite());
        this.igniteEntityTime = Math.max(data.getIgniteEntityTime(), 0);
        this.damageAmount = (float) MathHelper.clamp(data.getDamageAmount() * SyncConfig.DAMAGE_BASE_MULTIPLIER.get(), 0, Double.MAX_VALUE);
        // 霰弹情况，每个伤害要扣去
        if (data.getBulletAmount() > 1) {
            this.damageModifier = 1f / data.getBulletAmount();
        }
        this.knockback = MathHelper.clamp(data.getKnockback(), 0, Float.MAX_VALUE);
        this.dataTracker.set(EntityKineticBullet.pierce, MathHelper.clamp(data.getPierce(), 1, Integer.MAX_VALUE));
        this.extraDamage = data.getExtraDamage();
        ExplosionData explosionData = data.getExplosionData();
        if (explosionData != null) {
            this.dataTracker.set(EntityKineticBullet.hasExplosion, true);
            this.dataTracker.set(EntityKineticBullet.explosionDamage,
                    (float) MathHelper.clamp(explosionData.getDamage() * SyncConfig.DAMAGE_BASE_MULTIPLIER.get(),
                            0, Float.MAX_VALUE));
            this.dataTracker.set(EntityKineticBullet.explosionRadius,
                    MathHelper.clamp(explosionData.getRadius(), 0, Float.MAX_VALUE));
            this.explosionKnockback = explosionData.isKnockback();
            // 防止越界，提前判定
            int delayTickCount = explosionData.getDelay() * 20;
            if (delayTickCount < 0) {
                delayTickCount = Integer.MAX_VALUE;
            }
            this.explosionDelayCount = Math.max(delayTickCount, 1);
        }
        // 子弹初始位置重置
        double posX = thrower.lastRenderX + (thrower.getX() - thrower.lastRenderX) / 2.0;
        double posY = thrower.lastRenderY + (thrower.getY() - thrower.lastRenderY) / 2.0 + thrower.getStandingEyeHeight();
        double posZ = thrower.lastRenderZ + (thrower.getZ() - thrower.lastRenderZ) / 2.0;
        this.setPosition(posX, posY, posZ);
        this.startPos = this.getPos();
        this.dataTracker.set(EntityKineticBullet.isTracerAmmo, isTracerAmmo);
    }

    public static void createExplosion(Entity owner, Entity exploder, float damage, float radius, boolean knockback, Vec3d hitPos) {
        // 客户端不执行
        if (!(exploder.getWorld() instanceof ServerWorld level)) {
            return;
        }
        // 依据配置文件读取方块破坏方式
        Explosion.DestructionType mode = Explosion.DestructionType.KEEP;
        if (AmmoConfig.EXPLOSIVE_AMMO_DESTROYS_BLOCKS.get()) {
            mode = Explosion.DestructionType.DESTROY;
        }
        // 创建爆炸
        final boolean isFire = AmmoConfig.EXPLOSIVE_AMMO_FIRE.get();
        ProjectileExplosion explosion = new ProjectileExplosion(level, owner, exploder, null,
                null, hitPos.getX(), hitPos.getY(), hitPos.getZ(), damage, radius, knockback, isFire,
                mode);
        // 执行爆炸逻辑
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(true);
        if (mode == Explosion.DestructionType.KEEP) {
            explosion.clearAffectedBlocks();
        }
        // 客户端发包，发送爆炸相关信息
        level.getPlayers().stream().filter(player -> MathHelper.sqrt((float) player.squaredDistanceTo(hitPos)) < AmmoConfig.EXPLOSIVE_AMMO_VISIBLE_DISTANCE.get()).forEach(player -> {
            ExplosionS2CPacket packet = new ExplosionS2CPacket(hitPos.getX(), hitPos.getY(), hitPos.getZ(), radius,
                    explosion.getAffectedBlocks(), explosion.getAffectedPlayers().get(player),
                    explosion.getDestructionType(), explosion.getParticle(), explosion.getEmitterParticle(),
                    explosion.getSoundEvent());
            player.networkHandler.sendPacket(packet);
        });
    }

    @Override
    @SuppressWarnings("all")
    public void tick() {
        super.tick();
        // 调用 TaC 子弹服务器事件
        this.onBulletTick();
        // 粒子效果
        if (this.getWorld().isClient) {
            AmmoParticleSpawner.addParticle(this, this.getGunId());
        }
        // 子弹模型的旋转与抛物线
        Vec3d velocity = this.getVelocity();
        double x = velocity.x;
        double y = velocity.y;
        double z = velocity.z;
        double distance = velocity.horizontalLength();
        this.setYaw((float) Math.toDegrees(MathHelper.atan2(x, z)));
        this.setPitch((float) Math.toDegrees(MathHelper.atan2(y, distance)));
        // 子弹初始的朝向设置
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }
        // 子弹运动时的旋转（不包含自转）
        this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
        this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
        // 子弹位置更新
        double nextPosX = this.getX() + x;
        double nextPosY = this.getY() + y;
        double nextPosZ = this.getZ() + z;
        this.setPosition(nextPosX, nextPosY, nextPosZ);
        updateRotation();
        float rawFriction = this.dataTracker.get(friction);
        float rawGravity = this.dataTracker.get(gravity);
        // 子弹入水后的调整
        if (this.isTouchingWater()) {
            for (int i = 0; i < 4; i++) {
                this.getWorld().addParticle(ParticleTypes.BUBBLE, nextPosX - x * 0.25F, nextPosY - y * 0.25F, nextPosZ - z * 0.25F, x, y, z);
            }
            // 在水中的阻力
            rawFriction = 0.4F;
            rawGravity *= 0.6F;
        }
        // 重力与阻力更新速度状态
        this.setVelocity(this.getVelocity().multiply(1 - rawFriction));
        this.setVelocity(this.getVelocity().add(0, -rawGravity, 0));
        // 子弹生命结束
        if (this.age >= this.dataTracker.get(life) - 1) {
            this.discard();
        }
    }

    // 子弹的逻辑处理
    protected void onBulletTick() {
        // 服务器端子弹逻辑
        if (!this.getWorld().isClient()) {
            // 延迟爆炸判定
            final boolean rawHasExplosion = this.dataTracker.get(hasExplosion);
            if (rawHasExplosion) {
                if (this.explosionDelayCount > 0) {
                    this.explosionDelayCount--;
                } else {
                    createExplosion(this.getOwner(), this, this.dataTracker.get(explosionDamage),
                            this.dataTracker.get(explosionRadius), this.explosionKnockback, this.getPos());
                    // 爆炸直接结束不留弹孔，不处理之后的逻辑
                    this.discard();
                    return;
                }
            }
            // 子弹在 tick 起始的位置
            Vec3d startVec = this.getPos();
            // 子弹在 tick 结束的位置
            Vec3d endVec = startVec.add(this.getVelocity());
            // 子弹的碰撞检测
            HitResult result = BlockRayTrace.rayTraceBlocks(this.getWorld(), new RaycastContext(startVec, endVec, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            BlockHitResult resultB = (BlockHitResult) result;
            if (resultB.getType() != HitResult.Type.MISS) {
                // 子弹击中方块时，设置击中方块的位置为子弹的结束位置
                endVec = resultB.getPos();
            }

            final int rawPierce = this.dataTracker.get(pierce);
            List<EntityResult> hitEntities = null;
            // 子弹的击中检测，穿透为 1 或者爆炸类弹药限制为一个实体穿透判定
            if (rawPierce <= 1 || rawHasExplosion) {
                EntityResult entityResult = this.findEntityOnPath(startVec, endVec);
                // 将单个命中是实体创建为单个内容的 list
                if (entityResult != null) {
                    hitEntities = Collections.singletonList(entityResult);
                }
            } else {
                hitEntities = this.findEntitiesOnPath(startVec, endVec);
            }
            // 当子弹击中实体时，进行被命中的实体读取
            if (hitEntities != null && !hitEntities.isEmpty()) {
                EntityResult[] hitEntityResult = hitEntities.toArray(new EntityResult[0]);
                // 对被命中的实体进行排序，按照距离子弹发射位置的距离进行升序排序
                for (int i = 0; (i < rawPierce || i < 1) && i < (hitEntityResult.length - 1); i++) {
                    int k = i;
                    for (int j = i + 1; j < hitEntityResult.length; j++) {
                        if (hitEntityResult[j].hitVec.distanceTo(startVec) < hitEntityResult[k].hitVec.distanceTo(startVec)) {
                            k = j;
                        }
                    }
                    EntityResult t = hitEntityResult[i];
                    hitEntityResult[i] = hitEntityResult[k];
                    hitEntityResult[k] = t;
                }
                for (EntityResult entityResult : hitEntityResult) {
                    result = new TacHitResult(entityResult);
                    this.onHitEntity((TacHitResult) result, startVec, endVec);
                    final int tempPierce = this.dataTracker.get(EntityKineticBullet.pierce) - 1;
                    this.dataTracker.set(pierce, tempPierce);
                    if (tempPierce < 1 || rawHasExplosion) {
                        // 子弹已经穿透所有实体，结束子弹的飞行
                        this.discard();
                        return;
                    }
                }
            }
            this.onHitBlock(resultB, startVec, endVec);
        }
    }

    @Nullable
    protected EntityResult findEntityOnPath(Vec3d startVec, Vec3d endVec) {
        Vec3d hitVec = null;
        Entity hitEntity = null;
        boolean headshot = false;
        // 获取子弹 tick 路径上所有的实体
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), PROJECTILE_TARGETS);
        double closestDistance = Double.MAX_VALUE;
        Entity owner = this.getOwner();
        for (Entity entity : entities) {
            // 禁止对自己造成伤害（如有需要可以增加 Config 开启对自己的伤害）
            if (!entity.equals(owner)) {
                // 射击无视自己的载具
                if (owner != null && entity.equals(owner.getVehicle())) {
                    continue;
                }
                EntityResult result = this.getHitResult(entity, startVec, endVec);
                if (result == null) {
                    continue;
                }
                Vec3d hitPos = result.getHitPos();
                double distanceToHit = startVec.distanceTo(hitPos);
                if (entity.isAlive()) {
                    if (distanceToHit < closestDistance) {
                        hitVec = hitPos;
                        hitEntity = entity;
                        closestDistance = distanceToHit;
                        headshot = result.isHeadshot();
                    }
                }
            }
        }
        return hitEntity != null ? new EntityResult(hitEntity, hitVec, headshot) : null;
    }

    @Nullable
    protected List<EntityResult> findEntitiesOnPath(Vec3d startVec, Vec3d endVec) {
        List<EntityResult> hitEntities = new ArrayList<>();
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), PROJECTILE_TARGETS);
        Entity owner = this.getOwner();
        for (Entity entity : entities) {
            if (!entity.equals(owner)) {
                if (owner != null && entity.equals(owner.getVehicle())) {
                    continue;
                }
                EntityResult result = this.getHitResult(entity, startVec, endVec);
                if (result == null) {
                    continue;
                }
                if (entity.isAlive()) {
                    hitEntities.add(result);
                }
            }
        }
        return hitEntities;
    }

    @Nullable
    protected EntityResult getHitResult(Entity entity, Vec3d startVec, Vec3d endVec) {
        Box boundingBox = HitboxHelper.getFixedBoundingBox(entity, this.getOwner());
        // 计算射线与实体 boundingBox 的交点
        Vec3d hitPos = boundingBox.raycast(startVec, endVec).orElse(null);
        // 爆头判定
        if (hitPos == null) {
            return null;
        }
        Vec3d hitBoxPos = hitPos.subtract(entity.getPos());
        Identifier entityId = Registries.ENTITY_TYPE.getId(entity.getType());
        // 有配置的调用配置
        Box aabb = HeadShotAABBConfigRead.getAABB(entityId);
        if (aabb != null) {
            return new EntityResult(entity, hitPos, aabb.contains(hitBoxPos));
        }
        // 没有配置的默认给一个
        boolean headshot = false;
        float eyeHeight = entity.getStandingEyeHeight();
        if ((eyeHeight - 0.25) < hitBoxPos.y && hitBoxPos.y < (eyeHeight + 0.25)) {
            headshot = true;
        }
        return new EntityResult(entity, hitPos, headshot);
    }

    protected void onHitEntity(TacHitResult result, Vec3d startVec, Vec3d endVec) {
        if (result.getEntity() instanceof ITargetEntity targetEntity) {
            DamageSource source = this.getDamageSources().thrown(this, this.getOwner());
            targetEntity.onProjectileHit(this, result, source, this.getDamage(result.getPos()));
            // 打靶直接返回
            return;
        }
        // 获取Pre事件必要的信息
        Entity entity = result.getEntity();
        @Nullable Entity owner = this.getOwner();
        // 攻击者
        LivingEntity attacker = owner instanceof LivingEntity ? (LivingEntity) owner : null;
        boolean headshot = result.isHeadshot();
        float damage = this.getDamage(result.getPos());
        float headShotMultiplier = 1f;
        if (this.extraDamage != null && this.extraDamage.getHeadShotMultiplier() > 0) {
            headShotMultiplier = (float) (this.extraDamage.getHeadShotMultiplier() * SyncConfig.HEAD_SHOT_BASE_MULTIPLIER.get());
        }
        // 发布Pre事件
        var preEvent = new EntityHurtByGunEvent.Pre(entity, attacker, this.getGunId(), damage, headshot,
                headShotMultiplier, LogicalSide.SERVER);
        if (preEvent.post()) {
            return;
        }
        // 刷新由Pre事件修改后的参数
        entity = preEvent.getHurtEntity();
        // 受击目标
        LivingEntity livingEntity = entity instanceof EnderDragonPart part ? part.owner : (entity instanceof LivingEntity ? (LivingEntity) entity : null);
        attacker = preEvent.getAttacker();
        var newGunId = preEvent.getGunId();
        damage = preEvent.getBaseAmount();
        headshot = preEvent.isHeadShot();
        headShotMultiplier = preEvent.getHeadshotMultiplier();
        if (entity == null) {
            return;
        }
        // 点燃
        if (this.dataTracker.get(hasIgnite) && AmmoConfig.IGNITE_ENTITY.get()) {
            entity.setOnFireFor(this.igniteEntityTime);
        }
        // TODO 暴击判定（不是爆头）暴击判定内部逻辑，需要输出一个是否暴击的 flag
        if (headshot) {
            // 默认爆头伤害是 1x
            damage *= headShotMultiplier;
        }
        // 对 LivingEntity 进行击退强度的自定义
        if (livingEntity != null) {
            // 取消击退效果，设定自己的击退强度
            KnockBackModifier modifier = KnockBackModifier.fromLivingEntity(livingEntity);
            modifier.setKnockBackStrength(this.knockback);
            // 创建伤害
            tacAttackEntity(entity, damage);
            // 恢复原位
            modifier.resetKnockBackStrength();
        } else {
            // 创建伤害
            tacAttackEntity(entity, damage);
        }
        // 爆炸逻辑
        if (this.dataTracker.get(hasExplosion)) {
            // 取消无敌时间
            entity.timeUntilRegen = 0;
            createExplosion(this.getOwner(), this, this.dataTracker.get(explosionDamage),
                    this.dataTracker.get(explosionRadius), this.explosionKnockback, result.getPos());
        }
        // 只对 LivingEntity 执行击杀判定
        if (livingEntity != null) {
            // 事件同步，从服务端到客户端
            if (!getWorld().isClient) {
                int attackerId = attacker == null ? 0 : attacker.getId();
                // 如果生物死了
                if (livingEntity.isDead()) {
                    new EntityKillByGunEvent(livingEntity, attacker, newGunId, headshot, LogicalSide.SERVER).post();
                    NetworkClientHandler.GUN_KILL.sendToAllPlayers(new GunKillS2CPacket(livingEntity.getId(), attackerId, newGunId,
                            headshot), livingEntity.getWorld());
                } else {
                    new EntityHurtByGunEvent.Post(livingEntity, attacker, newGunId, damage, headshot, headShotMultiplier, LogicalSide.SERVER).post();
                    NetworkClientHandler.GUN_HURT.sendToAllPlayers(new GunHurtS2CPacket(livingEntity.getId(), attackerId, newGunId,
                            damage, headshot, headShotMultiplier), livingEntity.getWorld());
                }
            }
        }
    }

    protected void onHitBlock(BlockHitResult result, Vec3d startVec, Vec3d endVec) {
        super.onBlockHit(result);
        if (result.getType() == HitResult.Type.MISS) {
            return;
        }
        Vec3d hitVec = result.getPos();
        BlockPos pos = result.getBlockPos();
        // 触发事件

        if (new AmmoHitBlockEvent(this.getWorld(), result, this.getWorld().getBlockState(pos), this).post()) {
            return;
        }
        // 爆炸
        if (this.dataTracker.get(hasExplosion)) {
            createExplosion(this.getOwner(), this, this.dataTracker.get(explosionDamage),
                    this.dataTracker.get(explosionRadius), this.explosionKnockback, hitVec);
            // 爆炸直接结束不留弹孔，不处理之后的逻辑
            this.discard();
            return;
        }
        // 弹孔与点燃特效
        final boolean hasIgnite = this.getDataTracker().get(EntityKineticBullet.hasIgnite);
        if (this.getWorld() instanceof ServerWorld serverLevel) {
            BulletHoleOption bulletHoleOption = new BulletHoleOption(result.getSide(), result.getBlockPos(),
                    this.dataTracker.get(ammoId), this.dataTracker.get(gunId));
            serverLevel.spawnParticles(bulletHoleOption, hitVec.x, hitVec.y, hitVec.z, 1, 0, 0, 0, 0);
            if (hasIgnite) {
                serverLevel.spawnParticles(ParticleTypes.LAVA, hitVec.x, hitVec.y, hitVec.z, 1, 0, 0, 0, 0);
            }
        }
        if (hasIgnite && AmmoConfig.IGNITE_BLOCK.get()) {
            BlockPos offsetPos = pos.offset(result.getSide());
            if (AbstractFireBlock.canPlaceAt(this.getWorld(), offsetPos, result.getSide())) {
                BlockState fireState = AbstractFireBlock.getState(this.getWorld(), offsetPos);
                this.getWorld().setBlockState(offsetPos, fireState, Block.NOTIFY_ALL_AND_REDRAW);
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.LAVA, hitVec.x - 1.0 + this.random.nextDouble() * 2.0, hitVec.y, hitVec.z - 1.0 + this.random.nextDouble() * 2.0, 4, 0, 0, 0, 0);
            }
        }
        this.discard();
    }

    // 根据距离进行伤害衰减设计
    public float getDamage(Vec3d hitVec) {
        // 如果没有额外伤害，直接原样返回
        if (this.extraDamage == null) {
            return Math.max(0F, this.damageAmount * this.damageModifier);
        }
        // 调用距离伤害函数进行具体伤害计算
        var damageDecay = extraDamage.getDamageAdjust();
        // 距离伤害函数为空，直接全程默认伤害
        if (damageDecay == null || damageDecay.isEmpty()) {
            return Math.max(0F, this.damageAmount * this.damageModifier);
        }
        // 遍历进行判断
        double playerDistance = hitVec.distanceTo(this.startPos);
        for (ExtraDamage.DistanceDamagePair pair : damageDecay) {
            if (playerDistance < pair.getDistance()) {
                return (float) (Math.max(0F, pair.getDamage() * SyncConfig.DAMAGE_BASE_MULTIPLIER.get()) * this.damageModifier);
            }
        }
        // 如果忘记写最大值，那我就直接认为你伤害为 0
        return 0;
    }

    private void tacAttackEntity(Entity entity, float damage) {
        DamageSource source = ModDamageTypes.Sources.bullet(this.getWorld().getRegistryManager(), this, this.getOwner(), false);
        float armorIgnore = 0;
        if (this.extraDamage != null && this.extraDamage.getArmorIgnore() > 0) {
            armorIgnore = (float) (this.extraDamage.getArmorIgnore() * SyncConfig.ARMOR_IGNORE_BASE_MULTIPLIER.get());
        }
        // 给末影人造成伤害
        if (entity instanceof EndermanEntity) {
            source = this.getDamageSources().indirectMagic(this, getOwner());
        }
        // 穿甲伤害和普通伤害的比例计算
        float armorDamagePercent = MathHelper.clamp(armorIgnore, 0.0F, 1.0F);
        float normalDamagePercent = 1 - armorDamagePercent;
        // 取消无敌时间
        entity.timeUntilRegen = 0;
        // 普通伤害
        entity.damage(source, damage * normalDamagePercent);
        // 穿甲伤害
        source = ModDamageTypes.Sources.bullet(this.getWorld().getRegistryManager(), this, this.getOwner(), true);
        // 取消无敌时间
        entity.timeUntilRegen = 0;
        entity.damage(source, damage * armorDamagePercent);
    }

    public Identifier getAmmoId() {
        final String rawAmmoId = this.getDataTracker().get(EntityKineticBullet.ammoId);
        if (tempAmmoId == null || !tempAmmoId.toString().equals(rawAmmoId)) {
            tempAmmoId = Identifier.of(this.getDataTracker().get(ammoId));
        }
        return tempAmmoId;
    }

    public Identifier getGunId() {
        final String rawGunId = this.getDataTracker().get(EntityKineticBullet.gunId);
        if (tempGunId == null || !tempGunId.toString().equals(rawGunId)) {
            tempGunId = Identifier.of(this.getDataTracker().get(gunId));
        }
        return tempGunId;
    }

    @Override
    public void setYaw(float yaw) {
        if (this.dataTracker == null) {
            super.setYaw(yaw);
            return;
        }
        if (!this.getWorld().isClient) {
            this.dataTracker.set(EntityKineticBullet.yaw, yaw);
        }
        super.setYaw(yaw);
    }

    @Override
    public void setPitch(float pitch) {
        if (this.dataTracker == null) {
            super.setPitch(pitch);
            return;
        }
        if (!this.getWorld().isClient) {
            this.dataTracker.set(EntityKineticBullet.pitch, pitch);
        }
        super.setPitch(pitch);
    }

    @Override
    public float getPitch() {
        if (this.getWorld().isClient) {
            if (this.dataTracker == null) {
                return super.getPitch();
            }
            return this.dataTracker.get(EntityKineticBullet.pitch);
        }
        return super.getPitch();
    }

    @Override
    public float getYaw() {
        if (this.getWorld().isClient) {
            if (this.dataTracker == null) {
                return super.getYaw();
            }
            return this.dataTracker.get(EntityKineticBullet.yaw);
        }
        return super.getYaw();
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        if (this.dataTracker == null) {
            super.setVelocity(velocity);
            return;
        }
        if (!this.getWorld().isClient) {
            this.dataTracker.set(EntityKineticBullet.velocity, velocity);
        }
        super.setVelocity(velocity);
    }

    @Override
    public Vec3d getVelocity() {
        if (this.getWorld().isClient) {
            if (this.dataTracker == null) {
                return super.getVelocity();
            }
            return this.dataTracker.get(EntityKineticBullet.velocity);
        }
        return super.getVelocity();
    }

    public boolean isTracerAmmo() {
        return this.getDataTracker().get(isTracerAmmo);
    }

    public Vec3d getOriginCameraPosition() {
        return originCameraPosition;
    }

    public void setOriginCameraPosition(Vec3d originCameraPosition) {
        this.originCameraPosition = originCameraPosition;
    }

    public Vec3d getOriginRenderOffset() {
        return originRenderOffset;
    }

    public void setOriginRenderOffset(Vec3d originRenderOffset) {
        this.originRenderOffset = originRenderOffset;
    }

    @Override
    public boolean isOwner(@Nullable Entity entity) {
        if (entity == null) {
            return false;
        }
        return super.isOwner(entity);
    }

    public static class EntityResult {
        private final Entity entity;
        private final Vec3d hitVec;
        private final boolean headshot;

        public EntityResult(Entity entity, Vec3d hitVec, boolean headshot) {
            this.entity = entity;
            this.hitVec = hitVec;
            this.headshot = headshot;
        }

        // 子弹命中的实体
        public Entity getEntity() {
            return this.entity;
        }

        // 子弹命中的位置
        public Vec3d getHitPos() {
            return this.hitVec;
        }

        // 是否为爆头
        public boolean isHeadshot() {
            return this.headshot;
        }
    }
}
