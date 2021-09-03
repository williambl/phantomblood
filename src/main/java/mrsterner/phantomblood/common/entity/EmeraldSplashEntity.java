package mrsterner.phantomblood.common.entity;

import mrsterner.phantomblood.PhantomBlood;
import mrsterner.phantomblood.PhantomBloodClient;
import mrsterner.phantomblood.client.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

@EnvironmentInterface(value = EnvType.CLIENT, itf = FlyingItemEntity.class)
public class EmeraldSplashEntity extends PersistentProjectileEntity implements FlyingItemEntity {
    private static final TrackedData<Optional<UUID>> TARGET = DataTracker.registerData(EmeraldSplashEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private @Nullable Entity target;

    public EmeraldSplashEntity(EntityType<? extends EmeraldSplashEntity> entityType, World world) {
        super(entityType, world);
        setNoGravity(true);
    }

    public EmeraldSplashEntity(LivingEntity owner, Entity target, World world) {
        super(PhantomBlood.EMERALD_SPLASH_ENTITY_TYPE, owner, world);
        dataTracker.set(TARGET, Optional.of(target.getUuid()));
        this.pickupType = PickupPermission.DISALLOWED;
        this.target = target;
        setNoGravity(true);
        setPos(getX()+world.random.nextGaussian(), getY()+world.random.nextDouble(), getZ()+world.random.nextGaussian());
    }

    @Override
    public void tick() {
        super.tick();
        if (target != null && target.isAlive()) {
            if (!inGround && age > 10) {
                Vec3d vel = getVelocity();
                Vec3d dir = new Vec3d(target.getX(), target.getRandomBodyY(), target.getZ()).subtract(getPos()).normalize().multiply(0.2+world.random.nextGaussian()*0.05);
                if (Math.acos(vel.dotProduct(dir)/(vel.length()*dir.length())) > 0.3) {
                    setVelocity(getVelocity().multiply(0.85));
                }
                setVelocity(getVelocity().add(dir));
            }
        }
        if (world.isClient) {
            world.addParticle(new DustParticleEffect(0.0f, 1.0f, 0.0f, 1.0f), getX(), getY(), getZ(), 0.0, 0.0, 0.0);
        } else if (inGround || target == null || !target.isAlive()) {
            remove();
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(TARGET, Optional.empty());
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, PhantomBloodClient.PacketID);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getStack() {
        return Items.EMERALD.getDefaultStack();
    }
}
