package yee.pltision.randomadditions.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockProvider{
    default boolean canReplace(BlockState state){
        return state.isAir();
    }

    void place(BlockPos pos);
}