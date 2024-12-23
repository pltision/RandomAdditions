package yee.pltision.randomadditions.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

import static yee.pltision.randomadditions.piece.CreeperFernUtils.*;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yee.pltision.randomadditions.PertazAdditions;
import yee.pltision.randomadditions.network.CCreeperFireworkExplode;
import yee.pltision.randomadditions.network.Networks;

import java.util.Set;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster implements PowerableMob {
    @Shadow private int explosionRadius;

    protected CreeperMixin(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Inject(method = "explodeCreeper",at=@At(value = "HEAD"))
    public void explode(CallbackInfo ci){
        if(this.getType()==EntityType.CREEPER){
            if(!this.level().isClientSide){
                Networks.INSTANCE.send(
                        PacketDistributor.TRACKING_ENTITY.with(()->this),
                        new CCreeperFireworkExplode(this.getId(), this.getRandom().nextLong())
                );
            }
            Set<Entity> entities= findCanHitEntity(this,explosionRadius*3/2+2);
            hitEntities(this,explosionRadius*3/2+2,explosionRadius*8,entities,null);
            placeFern(this, PertazAdditions.CREEPER_FERN_SEEDLING.get().defaultBlockState());
        }
    }

    @Redirect(method = "explodeCreeper", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"))
    public Explosion replaceExplode(Level instance, Entity p_256599_, double p_255914_, double p_255684_, double p_255843_, float p_256310_, Level.ExplosionInteraction p_256178_){
        if(this.getType()==EntityType.CREEPER){
            return null;
        }
        else return instance.explode(p_256599_, p_255914_, p_255684_, p_255843_, p_256310_, p_256178_);
    }
}
