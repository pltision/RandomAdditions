package yee.pltision.randomadditions.event;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LevelEventEventListeners {

    @SubscribeEvent
    public static void mobSpawn(MobSpawnEvent.FinalizeSpawn event){
        if(!(event.getEntity()instanceof Zombie))return;
        event.getEntity().level().getCapability(EventCapabilityProvider.LEVEL_EVENT).ifPresent(events -> {
            for(LevelEvents.EventRecord eventRecord:events.events().values()){
                if(eventRecord.event instanceof AdditionDataEvent levelEvent){
                    levelEvent.finalSpawn(event);
                }
            }
        });
    }
}
