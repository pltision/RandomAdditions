package yee.pltision.randomadditions.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.MobSpawnEvent;

public interface AdditionDataEvent {
    default boolean freezeEntity(Entity entity){
        return false;
    }

    default void finalSpawn(MobSpawnEvent.FinalizeSpawn event){}
}
