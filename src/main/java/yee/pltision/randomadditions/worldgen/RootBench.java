package yee.pltision.randomadditions.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static yee.pltision.randomadditions.worldgen.WhatABigTree.square;

public class RootBench extends UpBench{
    public RootBench(Vector2f vector, int placeHeight, int length, BlockProvider wood, BlockProvider leaves){
        super(vector,placeHeight,length,wood,leaves);

        this.yMovement=-1f;
        this.yMul =0.9f;
    }

    @Override
    public void placeLeaves(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z) {
    }

    @Override
    public void placeLittleFork(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z, Vector3f direction, int length) {
    }

    @Override
    public void bigBranchBlock(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z) {
        square(level,pos,x,y-2,z,2, wood);
        square(level,pos,x,y-1,z,2, wood);
        square(level,pos,x,y,z,2, wood);
        square(level,pos,x,y+1,z,1, wood);
    }
    public void placeBench(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z){
        Vector3f place=new Vector3f();
        Vector3f fork=new Vector3f();
        float dy=yMovement;
        for(int i=0;i<length;i++){
            if(i<length/2)
                bigBranchBlock(level,pos, (int) (x+place.x), (int) (y+place.y), (int) (z+place.z));
            else
                smallBranchBlock(level,pos, (int) (x+place.x), (int) (y+place.y), (int) (z+place.z));

            place.x+= vector.x;
            place.y+= dy;
            place.z+= vector.y;
            dy*= yMul;
        }
        fork.set(vector.x,getLastForkMovement(),vector.y);
    }
}
