package yee.pltision.randomadditions.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yee.pltision.randomadditions.event.AdditionDataEvent;
import yee.pltision.randomadditions.event.EventCapabilityProvider;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    /**
     * 实在没找到在更新冻结到baseTick之间的事件了，<s>不过好像也能用MobEffect哎</s>并不行
     */
    @Inject(method="aiStep",at=@At("HEAD"))
    public void injectAiStep(CallbackInfo ci){
        this.level().getCapability(EventCapabilityProvider.LEVEL_EVENT).ifPresent(
                events-> events.events().values().forEach(eventRecord-> {
                    if (eventRecord.event instanceof AdditionDataEvent event) {

                        //冻结实体
                        if (event.freezeEntity(this)) {
                            this.setIsInPowderSnow(true);
                        }

                    }
                })
        );
    }

}
