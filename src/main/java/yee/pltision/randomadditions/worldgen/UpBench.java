package yee.pltision.randomadditions.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix2f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static yee.pltision.randomadditions.worldgen.WhatABigTree.cycle;
import static yee.pltision.randomadditions.worldgen.WhatABigTree.square;

public class UpBench implements Bench {

    Matrix2f ROTATE_P90=new Matrix2f().rotate(Mth.HALF_PI);
    Matrix2f ROTATE_N90=new Matrix2f().rotate(-Mth.HALF_PI);

    public  static Vector2f[] DIRECTIONS =new Vector2f[]{
            new Vector2f(0,1),
            new Vector2f(1,1),
            new Vector2f(1,0),
            new Vector2f(1,-1),
            new Vector2f(0,-1),
            new Vector2f(-1,-1),
            new Vector2f(-1,0),
            new Vector2f(-1,1),
    };
        
    UpBench(){}  //package private

    public UpBench(Vector2f vector, int placeHeight, int length, BlockProvider wood,BlockProvider leaves){
        this.vector=vector;
        this.placeHeight=placeHeight;
        this.length=length;
        this.wood = wood;
        this.leaves=leaves;
    }

    BlockProvider wood;
    BlockProvider leaves;
    int placeHeight;
    int length;
    Vector2f vector;
    float yMul =0.9f;
    float yMovement=2f;

    public void bigBranchBlock(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z){
        square(level,pos,x,y-2,z,1, wood);
        square(level,pos,x,y-1,z,1, wood);
        square(level,pos,x,y,z,2, wood);
        square(level,pos,x,y+1,z,1, wood);
    }
    public void smallBranchBlock(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z){
        square(level,pos,x,y-2,z,1, wood);
        square(level,pos,x,y-1,z,1, wood);
    }

    public void placeLittleFork(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z,
                                Vector3f direction,int length){
        Vector3f place=new Vector3f();
        for(int i=0;i<length;i++){
            place.add(direction);
            BlockState state=level.getBlockState(
                    pos.set(x+(int)place.x,y+(int)place.y,z+(int)place.z)
            );
            if(wood.canReplace(state)){
                wood.place(pos);
            }
        }

    }

    @Override
    public int placeHeight() {
        return placeHeight;
    }

    @Override
    public Vector2f direction() {
        return vector;
    }

    public void rotate(Vector2f vector,Matrix2f matrix){
        vector.mul(matrix);
        vector.x= Mth.clamp(vector.x,-1,1);
        vector.y= Mth.clamp(vector.y,-1,1);
    }

    int getForkLength(int i){   //package private
        return ((i&1)==0?2:5)+(length-i)/3;
    }
    float getForkMovement(int i){   //package private
        return -0.5f*(length-i)/length;
    }
    int getLastForkLength(){   //package private
        return length/4;
    }
    float getLastForkMovement(){   //package private
        return 0;
    }

    public void placeBench(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z){
        Vector3f place=new Vector3f();
        Vector2f rotate=new Vector2f();
        Vector3f fork=new Vector3f();
        float dy=yMovement;
        for(int i=0;i<length;i++){
            if(i<length/2)
                bigBranchBlock(level,pos, (int) (x+place.x), (int) (y+place.y), (int) (z+place.z));
            else
                smallBranchBlock(level,pos, (int) (x+place.x), (int) (y+place.y), (int) (z+place.z));

            if((i+1)%3==0){
                rotate(rotate.set(vector),ROTATE_P90);
                fork.set(rotate.x,getForkMovement(i),rotate.y);
                placeLittleFork(level,pos,(int) (x+place.x), (int) (y+place.y)-1, (int) (z+place.z),fork,getForkLength(i));
            }
            if((i+1)%3==0){
                rotate(rotate.set(vector),ROTATE_N90);
                fork.set(rotate.x,getForkMovement(i),rotate.y);
                placeLittleFork(level,pos,(int) (x+place.x), (int) (y+place.y)-1, (int) (z+place.z),fork,getForkLength(i));
            }

            place.x+= vector.x;
            place.y+= dy;
            place.z+= vector.y;
            dy*= yMul;
        }
        fork.set(vector.x,getLastForkMovement(),vector.y);
        placeLittleFork(level,pos,(int) (x+place.x), (int) (y+place.y)-1, (int) (z+place.z),fork,getLastForkLength());
    }

    public void placeLittleForkLeaves(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z,
                                Vector3f direction,int length){
        Vector3f place=new Vector3f();
        for(int i=0;i<length;i++){
            place.add(direction);
            if((i&1)==0){
                cycle(level,pos,(int) (x+place.x), (int) (y+place.y)-2, (int) (z+place.z),2.5f,leaves);
                cycle(level,pos,(int) (x+place.x), (int) (y+place.y-1), (int) (z+place.z),3.5f,leaves);
                cycle(level,pos,(int) (x+place.x), (int) (y+place.y), (int) (z+place.z),3.5f,leaves);
                cycle(level,pos,(int) (x+place.x), (int) (y+place.y)+1, (int) (z+place.z),3.5f,leaves);
                cycle(level,pos,(int) (x+place.x), (int) (y+place.y)+2, (int) (z+place.z),2.5f,leaves);
            }
        }

    }

    @Override
    public void placeLeaves(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z) {
        Vector3f place=new Vector3f();
        Vector2f rotate=new Vector2f();
        Vector3f fork=new Vector3f();
        float dy=yMovement;
        for(int i=0;i<length;i++){
            if(i>length*2/3){
                cycle(level,pos,(int) (x+place.x), (int) (y+place.y)-1, (int) (z+place.z),3,leaves);
            }

            cycle(level,pos,(int) (x+place.x), (int) (y+place.y), (int) (z+place.z),4,leaves);
            cycle(level,pos,(int) (x+place.x), (int) (y+place.y)+1, (int) (z+place.z),4,leaves);
            cycle(level,pos,(int) (x+place.x), (int) (y+place.y)+2, (int) (z+place.z),3,leaves);

            if((i+1)%3==0){
                rotate(rotate.set(vector),ROTATE_P90);
                fork.set(rotate.x,getForkMovement(i),rotate.y);
                placeLittleForkLeaves(level,pos,(int) (x+place.x), (int) (y+place.y)-1, (int) (z+place.z),fork,getForkLength(i));
            }
            if((i+1)%3==0){
                rotate(rotate.set(vector),ROTATE_N90);
                fork.set(rotate.x,getForkMovement(i),rotate.y);
                placeLittleForkLeaves(level,pos,(int) (x+place.x), (int) (y+place.y)-1, (int) (z+place.z),fork,getForkLength(i));
            }

            place.x+= vector.x;
            place.y+= dy;
            place.z+= vector.y;
            dy*= yMul;
        }
        fork.set(vector.x,getLastForkMovement(),vector.y);
        placeLittleForkLeaves(level,pos,(int) (x+place.x), (int) (y+place.y)-1, (int) (z+place.z),fork,getLastForkLength());
    }
}