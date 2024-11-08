package yee.pltision.randomadditions.item.projectile;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import yee.pltision.randomadditions.entity.FreezeArrow;

@SuppressWarnings("NullableProblems")
public class FreezeArrowItem extends ArrowItem {
    public FreezeArrowItem(Properties p_40512_) {
        super(p_40512_);
    }
    public AbstractArrow createArrow(Level p_40513_, ItemStack p_40514_, LivingEntity p_40515_) {
        return new FreezeArrow(p_40513_, p_40515_);
    }
}
