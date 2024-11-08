package yee.pltision.randomadditions.client;

import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
}
