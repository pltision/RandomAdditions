package yee.pltision.randomadditions.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

import static yee.pltision.randomadditions.piece.CreeperFernUtils.getCreeperExplodeFireworkTag;

public class CCreeperFireworkExplode {
    public final int entityId;
    public final long randomSeed;
    public final @Nullable CompoundTag fireworkTag;

    public static final long NULL_MARK=Long.MIN_VALUE;


    public CCreeperFireworkExplode(int entityId, long randomSeed) {
        this.entityId = entityId;
        //noinspection NumericOverflow
        this.randomSeed = randomSeed==NULL_MARK?NULL_MARK-1:randomSeed;
        this.fireworkTag = null;
    }
    public CCreeperFireworkExplode(int entityId,@NotNull CompoundTag fireworkTag) {
        this.entityId = entityId;
        this.randomSeed = NULL_MARK;
        this.fireworkTag = fireworkTag;
    }

    public CCreeperFireworkExplode(FriendlyByteBuf buf) {
        entityId= buf.readVarInt();
        randomSeed=buf.readVarLong();
        fireworkTag= randomSeed==NULL_MARK? buf.readNbt():null;

    }


    public void encode(FriendlyByteBuf buf){
        buf.writeVarInt(entityId);
        buf.writeVarLong(randomSeed);
    }
    public static void handle(CCreeperFireworkExplode msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    Level level=Minecraft.getInstance().level;
                    if(level!=null){
                        Entity entity=level.getEntity(msg.entityId);
                        if(entity!=null){
                            Vec3 vec3 = entity.getDeltaMovement();
                            entity.level().createFireworks(entity.getX(), entity.getY()+1.2, entity.getZ(), vec3.x, vec3.y, vec3.z,
                                    msg.fireworkTag==null?getCreeperExplodeFireworkTag(entity,new Random(msg.randomSeed)):msg.fireworkTag
                            );
                            entity.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 3.0F, 1.0F);
                        }
                    }
                })
        );
        ctx.get().setPacketHandled(true);
    }
}
