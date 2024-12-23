package yee.pltision.randomadditions.worldgen.terrablender;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import yee.pltision.randomadditions.PertazAdditions;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class Rules {

    public static ResourceKey<NormalNoise.NoiseParameters> createKey(String p_189310_) {
        return ResourceKey.create(Registries.NOISE, PertazAdditions.location(p_189310_));
    }
    static ResourceKey<NormalNoise.NoiseParameters> BEDROCK_BASE=createKey("bedrock_base");
    static ResourceKey<NormalNoise.NoiseParameters> BEDROCK_SUB=createKey("bedrock_sub");

    public static RuleSource makeRules(){
//        System.out.println("yee132131");

        return sequence(
                and(
                        state(Blocks.BEDROCK.defaultBlockState()),
//                        not(noiseYCheck(VerticalAnchor.aboveBottom(64),2,BEDROCK_BASE,5)),
                        noiseCondition(BEDROCK_BASE,-0.1,0.1)
//                        noiseCondition(BEDROCK_SUB,-1,2)
                )
//                state(Blocks.REDSTONE_BLOCK.defaultBlockState())
        );
    }

    public static SurfaceRules.RuleSource and(SurfaceRules.RuleSource then, ConditionSource... conditions){
        for(ConditionSource condition:conditions){
            then=ifTrue(condition,then);
        }
        return then;
    }
}
