package yee.pltision.randomadditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings({"NullableProblems"})
public class CloudGlassBlock extends CloudBlock{
    /*public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;*/

    VoxelShape HALF_AND_HALF = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public CloudGlassBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState block, BlockGetter level,  BlockPos pos, CollisionContext p_60575_) {
        return HALF_AND_HALF;
    }


    public boolean skipRendering(BlockState p_53972_, BlockState p_53973_, Direction p_53974_) {
        return p_53974_!=Direction.UP && super.skipRendering(p_53972_, p_53973_, p_53974_);
    }
    @Override
    public BlockState updateShape(BlockState p_53226_, Direction p_53227_, BlockState p_53228_, LevelAccessor level, BlockPos pos, BlockPos p_53231_) {
        return super.updateShape(p_53226_, p_53227_, p_53228_, level, pos, p_53231_);
    }
}
