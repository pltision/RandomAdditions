package yee.pltision.randomadditions.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import yee.pltision.randomadditions.PertazAdditions;

import static yee.pltision.randomadditions.PertazAdditions.MODID;
import static yee.pltision.randomadditions.datagen.PraTags.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void generateData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        var blockTagProvider = new BlockTagsProvider(generator.getPackOutput(),event.getLookupProvider(), MODID,event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.@NotNull Provider p_256380_) {
                tag(CLOUD).add(
                        PertazAdditions.CLOUD.block(),
                        PertazAdditions.GRASS_CLOUD.block()
                );
            }
        };
        generator.addProvider(event.includeServer(), blockTagProvider);

        var itemTagProvider = new ItemTagsProvider(generator.getPackOutput(),event.getLookupProvider(), blockTagProvider.contentsGetter(), MODID,event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.@NotNull Provider p_256380_) {
                tag(ItemTags.ARROWS).add(PertazAdditions.FREEZE_ARROW_ITEM.get());
            }
        };
        generator.addProvider(event.includeServer(), itemTagProvider);
    }


}