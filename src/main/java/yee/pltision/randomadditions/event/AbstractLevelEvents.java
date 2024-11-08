package yee.pltision.randomadditions.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.HashMap;
import java.util.Map;

public class AbstractLevelEvents implements LevelEvents {
    Map<ResourceLocation, LevelEventCreator>eventCreators;
    Map<LevelEvent, EventRecord> events=new HashMap<>();

    public AbstractLevelEvents(Map<ResourceLocation, LevelEventCreator> eventCreators) {
        this.eventCreators = eventCreators;
    }

    @Override
    public Map<ResourceLocation, LevelEventCreator> eventCreators() {
        return eventCreators;
    }

    @Override
    public Map<LevelEvent, EventRecord> events() {
        return events;
    }

    @Override
    public void removeEvent(LevelEvent event) {
        events.remove(event);
    }

    @Override
    public void tick(ServerLevel level) {
        eventCreators.values().forEach(creator->{
            LevelEvent event= creator.tryCreate(level,this);
            if(event!=null){
                events.put(event,new EventRecord(event));
            }
        });
        events.entrySet().removeIf(entry -> entry.getValue().tick(level, this));
    }


}
