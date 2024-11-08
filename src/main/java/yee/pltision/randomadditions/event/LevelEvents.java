package yee.pltision.randomadditions.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.Map;

public interface LevelEvents {

    Map<ResourceLocation, LevelEventCreator> eventCreators();

    Map<LevelEvent,EventRecord> events();
    void removeEvent(LevelEvent event);

    void tick(ServerLevel level);

    class EventRecord{
        public final LevelEvent event;
        public int tickCount;

        public EventRecord(LevelEvent event) {
            this.event = event;
        }

        public boolean tick(ServerLevel level,LevelEvents events){
            boolean result=event.tick(level,events,tickCount);
            tickCount++;
            return result;
        }

        @Override
        public String toString() {
            return "EventRecord{" +
                    "event=" + event +
                    ", tickCount=" + tickCount +
                    '}';
        }
    }
}
