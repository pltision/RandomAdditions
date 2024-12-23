package yee.pltision.randomadditions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

import static yee.pltision.randomadditions.piece.CreeperFernUtils.*;

/*@Mod.EventBusSubscriber
public class Listeners {
    @SubscribeEvent
    public static void entityExplosion(ExplosionEvent.Start event){
        Entity entity= event.getExplosion().getExploder();
        if(entity instanceof LivingEntity livingEntity && entity.getType()==EntityType.CREEPER){

            Set<Entity> entities=findCanHitEntity(livingEntity,5);
            hitEntities(livingEntity,5,20,entities,event.getExplosion().getIndirectSourceEntity());

            placeFern(livingEntity,PertazAdditions.CREEPER_FERN_SEEDLING.get().defaultBlockState());

            event.setCanceled(true);
        }
    }



}*/
