package yee.pltision.randomadditions.event;

import net.minecraft.server.level.ServerLevel;

public interface LevelEvent {
    /**
     * @return If true, remove event.
     */
    boolean tick(ServerLevel level, LevelEvents events,int tickCount);

}
