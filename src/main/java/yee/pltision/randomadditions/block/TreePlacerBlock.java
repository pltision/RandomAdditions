package yee.pltision.randomadditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import yee.pltision.randomadditions.worldgen.*;

public class TreePlacerBlock extends Block {

    public TreePlacerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        BlockProvider wood= pos -> p_60504_.setBlock(pos, Blocks.OAK_WOOD.defaultBlockState(),3);
        BlockProvider leaves= pos -> p_60504_.setBlock(pos, Blocks.OAK_LEAVES.defaultBlockState(),3);

        WhatABigTree whatABigTree=new WhatABigTree();
        whatABigTree.initRadius=4;
        whatABigTree.radiusAdd=-0.05f;
        whatABigTree.height=45;
        whatABigTree.provider=wood;

        ///fill ~-30 ~-30 ~-30 ~30 ~ ~30 air replace minecraft:oak_leaves

        whatABigTree.place(p_60504_,p_60505_,
                new Bench[]
                        {
                                new UpBench(UpBench.DIRECTIONS[0], 14,15,wood,leaves),
                                new UpBench(UpBench.DIRECTIONS[3], 17,17,wood,leaves),
                                new UpBench(UpBench.DIRECTIONS[4], 14,15,wood,leaves),
                                new UpBench(UpBench.DIRECTIONS[7], 18,17,wood,leaves),

                                new TopBench(UpBench.DIRECTIONS[0], 43,12,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[2], 43,12,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[4], 43,12,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[6], 43,12,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[1], 44,9,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[3], 44,9,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[5], 44,9,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[7], 44,9,wood,leaves),

                                new TopBench(UpBench.DIRECTIONS[0], 40,18,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[2], 40,18,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[4], 40,18,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[6], 40,18,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[1], 38,14,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[3], 38,14,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[5], 38,14,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[7], 38,14,wood,leaves),

                                new TopBench(UpBench.DIRECTIONS[0], 33,20,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[2], 33,20,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[4], 33,20,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[6], 33,20,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[1], 31,16,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[3], 31,16,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[5], 31,16,wood,leaves),
                                new TopBench(UpBench.DIRECTIONS[7], 31,16,wood,leaves),

                                new RootBench(UpBench.DIRECTIONS[0], 5,20,wood,leaves),
                                new RootBench(UpBench.DIRECTIONS[3], 5,20,wood,leaves),
                                new RootBench(UpBench.DIRECTIONS[4], 5,20,wood,leaves),
                                new RootBench(UpBench.DIRECTIONS[7], 5,20,wood,leaves),
                        }
        );

        return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);
    }
}
