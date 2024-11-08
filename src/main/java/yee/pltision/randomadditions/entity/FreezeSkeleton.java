package yee.pltision.randomadditions.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import yee.pltision.randomadditions.PertazAdditions;

import java.util.function.Predicate;

@SuppressWarnings("NullableProblems")
public class FreezeSkeleton extends Stray {
    public FreezeSkeleton(EntityType<? extends Stray> p_33836_, Level p_33837_) {
        super(p_33836_, p_33837_);
    }
    public ItemStack getProjectile(ItemStack p_33038_) {
        if (p_33038_.getItem() instanceof ProjectileWeaponItem) {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)p_33038_.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            return net.minecraftforge.common.ForgeHooks.getProjectile(this, p_33038_, itemstack.isEmpty() ? new ItemStack(PertazAdditions.FREEZE_ARROW_ITEM.get()) : itemstack);
        } else {
            return net.minecraftforge.common.ForgeHooks.getProjectile(this, p_33038_, ItemStack.EMPTY);
        }
    }
}
