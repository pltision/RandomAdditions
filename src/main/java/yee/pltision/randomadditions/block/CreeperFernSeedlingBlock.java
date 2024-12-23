package yee.pltision.randomadditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class CreeperFernSeedlingBlock extends TallGrassBlock {

    public static final IntegerProperty AGE= BlockStateProperties.AGE_1;

    Supplier<CreeperFernDoubleBlock> doubleBlock;


    public CreeperFernSeedlingBlock(Properties p_49795_,Supplier<CreeperFernDoubleBlock> doubleBlock) {
        super(p_49795_);
        this.doubleBlock=doubleBlock;
    }

    @Override
    public void randomTick(BlockState p_222954_, ServerLevel p_222955_, BlockPos p_222956_, RandomSource p_222957_) {
        int age=p_222954_.getValue(age());
        if(p_222954_.getValue(age())<maxAge()){
            p_222955_.setBlockAndUpdate(p_222956_,p_222954_.setValue(age(),age+1));
        }
        else doubleBlock.get().placeDouble(p_222955_, p_222956_,p_222954_,0);
    }

    public void performBonemeal(ServerLevel level, RandomSource p_222579_, BlockPos pos, BlockState state) {
        int age=state.getValue(age());
        if(age<maxAge()){
            level.setBlockAndUpdate(pos,state.setValue(age(),age+1));
        }
        else if (doubleBlock.get().placeDouble(level,pos,state,0)) {
            if(age!=maxAge())
                level.setBlockAndUpdate(pos,state.setValue(age(),age+1));
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_);
        p_49915_.add(age());
    }
    public IntegerProperty age(){
        return AGE;
    }
    public int maxAge(){
        return BlockStateProperties.MAX_AGE_1;
    }
}
