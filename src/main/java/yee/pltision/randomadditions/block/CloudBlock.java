package yee.pltision.randomadditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import yee.pltision.randomadditions.PertazAdditions;
import yee.pltision.randomadditions.datagen.PraTags;

import java.util.List;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class CloudBlock extends Block {
    public CloudBlock(Properties p_49795_) {
        super(p_49795_);
    }

    VoxelShape NEAR_BLOCK=Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    VoxelShape HALF=Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState block, @NotNull  BlockGetter level, @NotNull  BlockPos pos, @NotNull  CollisionContext p_60575_) {
        BlockState up=level.getBlockState(pos.above());
        BlockState down=level.getBlockState(pos.below());
        if(up.isCollisionShapeFullBlock(level,pos.above())){
            return super.getCollisionShape(block,level,pos,p_60575_);
        }
        else if( up.is(PraTags.CLOUD) || ( !down.is(PraTags.CLOUD) && !down.isCollisionShapeFullBlock(level,pos.below()) ) ){
            return HALF;
        }
        return Shapes.empty();
    }

    static NormalNoise.NoiseParameters CLOUD_PARAMETERS=new NormalNoise.NoiseParameters(-7, List.of(1d,0d,1d));

    public static final long RANDOM_ADD= PertazAdditions.MODID.hashCode()|(long)"喵呜~".hashCode()<<32;
    @Override
    public void tick(@NotNull BlockState state,@NotNull  ServerLevel level,@NotNull  BlockPos pos,@NotNull  RandomSource randomSource) {
        int flowTo=level.getGameRules().getInt(PertazAdditions.CLOUD_FLOW_HEIGHT);

        if(
                (
                        pos.getY()<flowTo-32 ||
                                pos.getY()<
                                NormalNoise.create(RandomSource.create(level.getSeed()^RANDOM_ADD),CLOUD_PARAMETERS)
                                    .getValue(pos.getX(),pos.getY(),pos.getZ())*16+flowTo
                )
                &&  level.getBlockState(pos.above()).isAir())
        {
            level.setBlockAndUpdate(pos.above(), state);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }
    public static int getFlowTick(int y,int flowTo){
        return Mth.clamp(15-(flowTo-y)/10,3,15);
    }

    public void onPlace(BlockState p_53233_, Level level, BlockPos p_53235_, BlockState p_53236_, boolean p_53237_) {
        level.scheduleTick(p_53235_, this, getFlowTick(p_53235_.getY(),level.getGameRules().getInt(PertazAdditions.CLOUD_FLOW_HEIGHT)));
    }

    public BlockState updateShape(BlockState p_53226_, Direction p_53227_, BlockState p_53228_, LevelAccessor level, BlockPos pos, BlockPos p_53231_) {
        if(level instanceof ServerLevel serverLevel){
            level.scheduleTick(pos, this, getFlowTick(pos.getY(),serverLevel.getGameRules().getInt(PertazAdditions.CLOUD_FLOW_HEIGHT)));
        }
        return super.updateShape(p_53226_, p_53227_, p_53228_, level, pos, p_53231_);
    }


    public int getLightBlock(BlockState p_54460_, BlockGetter p_54461_, BlockPos p_54462_) {
        return 0;
    }

    public boolean skipRendering(BlockState p_53972_, BlockState p_53973_, Direction p_53974_) {
        return p_53973_.is(PraTags.CLOUD) || super.skipRendering(p_53972_, p_53973_, p_53974_);
    }
}
