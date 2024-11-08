package yee.pltision.randomadditions.event;

import net.minecraft.server.level.ServerLevel;

public interface LevelEventCreator {
    LevelEvent tryCreate(ServerLevel level, LevelEvents events);
}
