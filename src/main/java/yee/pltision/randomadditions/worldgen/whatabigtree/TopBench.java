package yee.pltision.randomadditions.worldgen.whatabigtree;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.joml.Vector2f;

public class TopBench extends UpBench{
    public TopBench(Vector2f vector, int placeHeight, int length, BlockProvider wood, BlockProvider leaves){
        super(vector,placeHeight,length,wood,leaves);

        this.yMovement=0.5f;
//        this.yAcceleration=-0.025f;
    }

    @Override
    int getForkLength(int i) {
        return Math.min(i,length/2);
    }

    @Override
    float getForkMovement(int i) {
        return -0.3f;
    }
    int getLastForkLength(){   //package private
        return 0;
    }
    @Override
    float getLastForkMovement() {
        return -1f;
    }

    @Override
    public void bigBranchBlock(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z) {
        smallBranchBlock(level, pos, x, y, z);
    }
}
