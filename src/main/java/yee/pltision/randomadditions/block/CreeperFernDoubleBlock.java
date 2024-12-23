package yee.pltision.randomadditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;


@SuppressWarnings({"NullableProblems", "deprecation"})
public class CreeperFernDoubleBlock extends DoublePlantBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final IntegerProperty AGE= BlockStateProperties.AGE_1;

    @FunctionalInterface
    public interface CreeperSupplier {
        Entity createCreeper(Level level, BlockPos pos, BlockState state);
    }

    public CreeperSupplier creeperSupplier=(level, pos, state) -> {
        Creeper creeper=new Creeper(EntityType.CREEPER, level);
        creeper.setPos(new Vec3(pos.getX(),pos.getY(),pos.getZ()));
        return creeper;
    };

    public CreeperFernDoubleBlock(Properties p_52861_) {
        super(p_52861_);
    }

    public boolean placeDouble(ServerLevel level,BlockPos pos,BlockState seedling,int age){
        if(level.isEmptyBlock(pos.above())){
            BlockState state=defaultBlockState().setValue(AGE,Math.min(age,BlockStateProperties.MAX_AGE_2));
            level.setBlockAndUpdate(pos,state.setValue(HALF, DoubleBlockHalf.LOWER));
            level.setBlockAndUpdate(pos.above(),state.setValue(HALF, DoubleBlockHalf.UPPER));
            return false;
        }
        return true;
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_);
        p_49915_.add(age());
    }

    @Override
    public void tick(BlockState p_222945_, ServerLevel level, BlockPos p_222947_, RandomSource p_222948_) {
        super.tick(p_222945_, level, p_222947_, p_222948_);
        if(p_222945_.getValue(HALF)==DoubleBlockHalf.LOWER&&p_222945_.getValue(age())==maxAge()){
            level.addFreshEntity(creeperSupplier.createCreeper(level,p_222947_,p_222945_));

            level.destroyBlock(p_222947_,false);
            BlockPos upPos=p_222947_.above();
            BlockState up=level.getBlockState(upPos);
            if(up.is(this)&&up.getValue(HALF)==DoubleBlockHalf.UPPER){
                level.destroyBlock(upPos,false);
            }
        }
    }

    @Override
    public void randomTick(BlockState p_222954_, ServerLevel p_222955_, BlockPos p_222956_, RandomSource p_222957_) {
//        super.randomTick(p_222954_, p_222955_, p_222956_, p_222957_);
        if(p_222954_.getValue(HALF)==DoubleBlockHalf.LOWER){
            int age=p_222954_.getValue(age());
            BlockPos upPos=p_222956_.above();
            BlockState up=p_222955_.getBlockState(upPos);
            if(age<maxAge()){
                p_222955_.setBlockAndUpdate(p_222956_,p_222954_.setValue(AGE,age+1));
                if(up.is(this)&&up.getValue(HALF)==DoubleBlockHalf.UPPER){
                    p_222955_.setBlockAndUpdate(upPos,up.setValue(age(),age+1));
                }
            }
            else {
                p_222955_.playLocalSound(p_222956_, SoundEvents.CREEPER_HURT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                p_222955_.scheduleTick(p_222956_,this,20);
            }

        }
    }
    public IntegerProperty age(){
        return AGE;
    }
    public int maxAge(){
        return BlockStateProperties.MAX_AGE_1;
    }
}
