package yee.pltision.randomadditions.client.entityrenderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yee.pltision.randomadditions.PertazAdditions;
import yee.pltision.randomadditions.entity.FreezeArrow;

@SuppressWarnings("NullableProblems")
@OnlyIn(Dist.CLIENT)
public class FreezeArrowRenderer extends ArrowRenderer<FreezeArrow> {
   public static final ResourceLocation ARROW_LOCATION = PertazAdditions.location("textures/entity/projectiles/freeze_arrow.png");

   public FreezeArrowRenderer(EntityRendererProvider.Context p_174399_) {
      super(p_174399_);
   }

   public ResourceLocation getTextureLocation(FreezeArrow p_116001_) {
      return ARROW_LOCATION;
   }
}