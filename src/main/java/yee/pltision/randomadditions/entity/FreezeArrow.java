package yee.pltision.randomadditions.entity;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;
import yee.pltision.randomadditions.PertazAdditions;

@SuppressWarnings("NullableProblems")
public class FreezeArrow extends AbstractArrow {
    public FreezeArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
        if(level().isClientSide)
            options=new DustParticleOptions(new Vector3f(0.25f,0.8f,1f),1f);
    }
    public FreezeArrow(Level p_36722_, LivingEntity entity) {
        super(PertazAdditions.FREEZE_ARROW.get(), entity, p_36722_);
        if(level().isClientSide)
            options=new DustParticleOptions(new Vector3f(0.25f,0.8f,1f),1f);
    }

    @Override
    protected ItemStack getPickupItem() {
        return PertazAdditions.FREEZE_ARROW_ITEM.get().getDefaultInstance();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity p_36744_) {
        super.doPostHurtEffects(p_36744_);
        freeze(p_36744_);
    }

    @Override
    protected void onHitEntity(EntityHitResult p_36757_) {
        super.onHitEntity(p_36757_);
        if(!(p_36757_.getEntity() instanceof LivingEntity))
            freeze(p_36757_.getEntity());
    }

    public void freeze(Entity entity){
        int max= entity.getTicksRequiredToFreeze();
        entity.setTicksFrozen(Integer.min(entity.getTicksFrozen()+max/2,max*2));
    }

    DustParticleOptions options;
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(options, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }
}
