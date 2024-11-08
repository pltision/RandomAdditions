package yee.pltision.randomadditions.event;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerLevel;
import org.slf4j.Logger;


public class OverworldEventCreator implements LevelEventCreator {
    Logger LOGGER = LogUtils.getLogger();
    public static int MAX_WAIT=day(-3);

    int eventClod=day(0);
    LevelEventCreator waiting;

    WeightedPool<LevelEventCreator> eventPool;

    public OverworldEventCreator(WeightedPool<LevelEventCreator> eventPool){
        this.eventPool =eventPool;
    }

    public static int day(float day){
        return (int) (day*24000);
    }

    int nextCold(float random){
        return day(random+2.5f);
    }

    @Override
    public LevelEvent tryCreate(ServerLevel level, LevelEvents events) {
        eventClod--;
        if(eventClod<0){
            if(waiting==null) {
                for(LevelEvents.EventRecord eventRecord:events.events().values()){
                    if(eventRecord.event instanceof OverworldEvent){
                        eventClod=nextCold(level.random.nextFloat());
                        LOGGER.warn("pertaz'es Random Additions - LevelEvent: Event is still not finishing, reset clod time. {}", eventRecord);
                        return null;
                    }
                }
                waiting = eventPool.random(level.random::nextInt);
                eventClod=nextCold(level.random.nextFloat());
            }
            if(eventClod<MAX_WAIT){
                eventClod=nextCold(level.random.nextFloat());
                LOGGER.warn("pertaz'es Random Additions - LevelEvent: Event creator waited too long time! Replaced a new event. {} ", waiting);
                waiting= null;
            }
        }
        if(waiting!=null){
            LevelEvent event= waiting.tryCreate(level,events);
            if(event!=null){
                waiting=null;
                return event;
            }
        }
        return null;
    }

    interface OverworldEvent{}
}
