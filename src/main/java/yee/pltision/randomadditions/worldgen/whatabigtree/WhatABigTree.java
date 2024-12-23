package yee.pltision.randomadditions.worldgen.whatabigtree;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector2f;

public class WhatABigTree {
    public BlockProvider provider;

    public int height;

    public float initRadius;
    public float radiusAdd;

    public void place(LevelAccessor level, BlockPos origin, Bench[] benches){
        BlockPos.MutableBlockPos pos=new BlockPos.MutableBlockPos();
        int x=origin.getX(),y=origin.getY(),z=origin.getZ();


        //创建树干
        Vector2f benchPlace=new Vector2f();
        for(Bench bench:benches){
            float radius=(initRadius+radiusAdd*bench.placeHeight());
            int rr= (int) (radius*radius);
            Vector2f direction=bench.direction();
            benchPlace.set(0,0);
            while(benchPlace.x*benchPlace.x+benchPlace.y*benchPlace.y>rr){
                benchPlace.add(direction);
            }
            bench.placeBench(level,pos, (int) (x+benchPlace.x),y+bench.placeHeight(), (int) (z+benchPlace.y));
        }

        //创建树干
        {
            float radius = initRadius;
            for (int i = 0; i < height; i++) {
                cycle(level, pos, x, y + i, z, radius, provider);
                radius += radiusAdd;
            }
        }

        //创建树叶
        for(Bench bench:benches){
            float radius=(initRadius+radiusAdd*bench.placeHeight());
            int rr= (int) (radius*radius);
            Vector2f direction=bench.direction();
            benchPlace.set(0,0);
            while(benchPlace.x*benchPlace.x+benchPlace.y*benchPlace.y>rr){
                benchPlace.add(direction);
            }
            bench.placeLeaves(level,pos, (int) (x+benchPlace.x),y+bench.placeHeight(), (int) (z+benchPlace.y));
        }


    }

    public static void cycle(LevelAccessor level,BlockPos.MutableBlockPos pos,int x, int y,int z, float r,BlockProvider provider){
        int rr= (int) (r*r);
        for(int i = (int) -r; i<=r; i++){
            for(int j = (int) -r; j<=r; j++){
                if(i*i+j*j<rr){
                    pos.set(x+i,y,z+j);
                    BlockState state=level.getBlockState(pos);
                    if(provider.canReplace(state)){
                        provider.place(pos);
                    }
                }
            }
        }

    }


    public static void square(LevelAccessor level, BlockPos.MutableBlockPos pos, int x, int y, int z, int r, BlockProvider provider){
        for(int i = -r; i<r; i++){
            for(int j = -r; j<r; j++){
                pos.set(x+i,y,z+j);
                BlockState state=level.getBlockState(pos);
                if(provider.canReplace(state)){
                    provider.place(pos);
                }
            }
        }

    }

}
