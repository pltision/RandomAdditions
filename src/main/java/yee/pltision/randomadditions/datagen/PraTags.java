package yee.pltision.randomadditions.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static yee.pltision.randomadditions.PertazAdditions.location;

public interface PraTags {
    TagKey<Block> CLOUD=TagKey.create(Registries.BLOCK,location("cloud"));

}
