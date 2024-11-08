package yee.pltision.randomadditions.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.joml.Vector2f;

public interface Bench {
    int placeHeight();
    Vector2f direction();

    void placeBench(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z);
    void placeLeaves(LevelAccessor level,BlockPos.MutableBlockPos pos,int x,int y,int z);
}
