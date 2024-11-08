package yee.pltision.randomadditions.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import yee.pltision.randomadditions.PertazAdditions;

import java.util.Iterator;

public class FrostEvent implements LevelEvent , OverworldEventCreator.OverworldEvent ,AdditionDataEvent{

    @Override
    public boolean tick(ServerLevel level, LevelEvents events, int tickCount) {

        /*if(tickCount %10==0)
            level.getAllEntities().forEach(entity -> {
                if(entity instanceof LivingEntity livingEntity&& freezeEntity(livingEntity)){
                    livingEntity.addEffect(new MobEffectInstance(PertazAdditions.FREEZE_EFFECT.get()));
                }
            });*/


        if(tickCount>18000){
            level.players().forEach(
                    p -> p.sendSystemMessage(Component.literal("温度有所回升").withStyle(ChatFormatting.AQUA))
            );
            return true;
        }
        if(!level.dimensionType().hasFixedTime()&&(level.getDayTime()%24000<13000&&level.getDayTime()%24000>0)) {
            level.players().forEach(
                    p -> p.sendSystemMessage(Component.literal("随着太阳升起，温度有所回升").withStyle(ChatFormatting.AQUA))
            );
            return true;
        }
        return false;
    }

    public static LevelEventCreator creator(){
        return (level,events)->{
//            System.out.println(level.getTimeOfDay(1));
            if(level.dimensionType().hasFixedTime()||(level.getDayTime()%24000>=13000&&level.getDayTime()%24000<15000)){
                level.players().forEach(
                        p -> p.sendSystemMessage(Component.literal("空气逐渐凝固...").withStyle(ChatFormatting.AQUA))
                );
                return new FrostEvent();
            }
            return null;
        };
    }

    @Override
    public boolean freezeEntity(Entity entity) {
        return entity instanceof Player && (!entity.isOnFire())&&entity.level().getBrightness(LightLayer.SKY,entity.blockPosition())>0&&entity.level().getBrightness(LightLayer.BLOCK,entity.blockPosition())<3;
    }

    static final ItemStack[] LEATHER_ARMORS={new ItemStack(Items.LEATHER_BOOTS),new ItemStack(Items.LEATHER_LEGGINGS),new ItemStack(Items.LEATHER_CHESTPLATE),new ItemStack(Items.LEATHER_HELMET)};
    static final EquipmentSlot[] EQUIPMENT_SLOTS={EquipmentSlot.FEET,EquipmentSlot.LEGS,EquipmentSlot.CHEST,EquipmentSlot.HEAD};

    @Override
    public void finalSpawn(MobSpawnEvent.FinalizeSpawn event) {

        Iterator<ItemStack> armors= event.getEntity().getArmorSlots().iterator();
        ItemStack armor;

        int random=event.getEntity().getRandom().nextInt(4);
        int last=-1;
        for(int i=0;i<4;i++){
            armor=armors.next();
            if(armor.isEmpty()){
                last=i;
                if(i==random) break;
            }
        }
        if(last!=-1){
            event.getEntity().setItemSlot(EQUIPMENT_SLOTS[last],LEATHER_ARMORS[last].copy());
        }
    }
}
