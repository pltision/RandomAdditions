package yee.pltision.randomadditions.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yee.pltision.randomadditions.client.entityrenderer.*;

import static yee.pltision.randomadditions.PertazAdditions.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class InitializeClient {

    @SubscribeEvent
    public static void registryEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(FREEZE_ARROW.get(), FreezeArrowRenderer::new);
        event.registerEntityRenderer(FREEZE_SKELETON.get(), StrayRenderer::new);
    }

    @SubscribeEvent
    public static void registryBlockColors(RegisterColorHandlersEvent.Block event){
        event.register((p_276237_, p_276238_, p_276239_, p_276240_) -> p_276238_ != null && p_276239_ != null ? BiomeColors.getAverageGrassColor(p_276238_, p_276239_) : GrassColor.getDefaultColor(), CREEPER_FERN_SEEDLING.get(), CREEPER_FERN_DOUBLE.get());
    }
}
