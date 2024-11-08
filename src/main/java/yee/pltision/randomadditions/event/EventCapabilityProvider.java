package yee.pltision.randomadditions.event;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yee.pltision.randomadditions.PertazAdditions;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class EventCapabilityProvider implements ICapabilityProvider {
    public static final Capability<LevelEvents> LEVEL_EVENT= CapabilityManager.get(new CapabilityToken<>(){});

    LevelEvents events;

    LazyOptional<LevelEvents> optional=LazyOptional.of(()->events);

    public EventCapabilityProvider(LevelEvents events) {
        this.events = events;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap==LEVEL_EVENT?optional.cast(): LazyOptional.empty();
    }

    @SubscribeEvent
    public static void registryCapability(AttachCapabilitiesEvent<Level> event){
        if(event.getObject().dimension()==Level.OVERWORLD){
            event.addCapability(PertazAdditions.location("level_events"), new EventCapabilityProvider(new AbstractLevelEvents(new HashMap<>(Map.of(
                    /*PertazAdditions.location("test"), (level, events) -> events.events().isEmpty()?(level1, events1, tickCount) -> {
                        if(tickCount>100)
                            return true;
                        if(tickCount%20==0){
                            level1.getServer().getPlayerList().getPlayers().forEach(
                                    player->player.sendSystemMessage(Component.literal("喵呜呜"+tickCount))
                            );
                        }
                        return false;
                    }:null,*/
                    PertazAdditions.location("overwold"),(LevelEventCreator) new OverworldEventCreator(new WeightedPool<>(
                        WeightedPool.WeightedItem.wi(FrostEvent.creator(),10)
                    ))
            )))));
        }
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event){
        if(event.level instanceof ServerLevel serverLevel&&event.phase== TickEvent.Phase.START)
            serverLevel.getCapability(LEVEL_EVENT).ifPresent(cap->cap.tick(serverLevel));
    }

}
