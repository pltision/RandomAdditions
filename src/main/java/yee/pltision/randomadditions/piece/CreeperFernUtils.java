package yee.pltision.randomadditions.piece;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import yee.pltision.randomadditions.mixin.CreeperMixin;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CreeperFernUtils {


    public static CompoundTag CREEPER_EXPLODE_FIREWORK_TAG=new CompoundTag();

    public static int[] COLOR_LIST={
            DyeColor.WHITE.getFireworkColor(),
            DyeColor.PINK.getFireworkColor(),
            DyeColor.LIGHT_BLUE.getFireworkColor(),
            DyeColor.PURPLE.getFireworkColor(),
            DyeColor.ORANGE.getFireworkColor(),
            DyeColor.YELLOW.getFireworkColor(),
            DyeColor.LIME.getFireworkColor(),
    };
//    public static FireworkRocketItem.Shape[] SHAPES={}

    public static final String COLORS="Colors";

    public static int[] makeColors(Random random){
        int init=3;
        int[] inits=new int [init];
        for(int i=0;i<init;i++)
            inits[i]=COLOR_LIST[random.nextInt(COLOR_LIST.length)];

        int next=init*2;
        int[] nexts=new int[next];
        for(int i=0;i<next;i++)
            nexts[i]=inits[random.nextInt(init)];

        return nexts;
    }

    public static CompoundTag star(FireworkRocketItem.Shape shape,Random random){
        CompoundTag  star=new CompoundTag();
        star.putIntArray(COLORS,makeColors(random));
        shape.save(star);
        return star;
    }

    public static List<BiConsumer<Random,Consumer<CompoundTag>>> GROUPS=Arrays.asList(
            (random, consumer)->{
                consumer.accept(star(FireworkRocketItem.Shape.SMALL_BALL,random));
                consumer.accept(star(FireworkRocketItem.Shape.LARGE_BALL,random));
                if(random.nextBoolean()) consumer.accept(star(FireworkRocketItem.Shape.STAR,random));
            },
            (random, consumer)->{
                consumer.accept(star(FireworkRocketItem.Shape.STAR,random));
                consumer.accept(star(FireworkRocketItem.Shape.STAR,random));
                if(random.nextBoolean()) consumer.accept(star(FireworkRocketItem.Shape.STAR,random));
                consumer.accept(star(FireworkRocketItem.Shape.SMALL_BALL,random));
            },
            (random, consumer)->{
                consumer.accept(star(FireworkRocketItem.Shape.LARGE_BALL,random));
                consumer.accept(star(FireworkRocketItem.Shape.CREEPER,random));
            }

    );


    public static CompoundTag getCreeperExplodeFireworkTag(Entity creeper,Random random){
        ListTag explosion=new ListTag();
        GROUPS.get(random.nextInt(GROUPS.size())).accept(random,explosion::add);

        CompoundTag firework=new CompoundTag();
        firework.put("Explosions",explosion);
        return firework;
    }


    public static void hitEntities(LivingEntity creeper, int radius, int damage, Set<Entity> entities, Entity indirect){
        for(Entity entity:entities){
            float dist= entity.distanceTo(creeper);
            float factor=Math.max(0,1-dist/radius);
            if(entity instanceof LivingEntity livingEntity){
                livingEntity.hurt(creeper.damageSources().explosion(creeper,indirect),factor*damage);
            }
            entity.setDeltaMovement(entity.getDeltaMovement().add((creeper.getX()-entity.getX())/dist*-factor,(creeper.getY()-entity.getY())/dist*-factor,(creeper.getZ()-entity.getZ())/dist*-factor));
        }
    }

    public static void placeFern(LivingEntity entity, BlockState state){
        RandomSource random =entity.getRandom();
        BlockPos pos=entity.blockPosition();
        BlockPos.MutableBlockPos test=new BlockPos.MutableBlockPos();

        boolean unPlaced=true;
        for(int i= random.nextInt(1);i<3;i++){
            int dx,dz;
            if(random.nextBoolean()){
                dx= random.nextInt(5,9)*(random.nextBoolean()?1:-1);
                dz= random.nextInt(9)*(random.nextBoolean()?1:-1);
            }
            else{
                dx= random.nextInt(9)*(random.nextBoolean()?1:-1);
                dz= random.nextInt(5,9)*(random.nextBoolean()?1:-1);
            }
            test.set(pos).move(dx,7,dz);
            for(int j=0;j<15;j++){
                if(state.canSurvive(entity.level(),test)){
                    entity.level().setBlockAndUpdate(test,state);
                    unPlaced=false;
                    break;
                }
                test.move(Direction.DOWN);
            }
        }
        if(unPlaced){
            for(int i= random.nextInt(1);i<3;i++){
                int dx= random.nextInt(-4,5);
                int dz= random.nextInt(-4,5);
                test.set(pos).move(dx,7,dz);
                for(int j=0;j<15;j++){
                    if(state.canSurvive(entity.level(),test)){
                        entity.level().setBlockAndUpdate(test,state);
                        break;
                    }
                    test.move(Direction.DOWN);
                }
            }
        }
    }

    public static boolean canWalk(boolean[] markers, int radius, int size, int x, int y, int z){
        return !( !(x>=-radius&&y>=-radius&&z>=-radius&&x<=radius&&y<=radius&&z<=radius) /*true则走不了*/ || /*true则走不了*/ markers[(x+radius)*size+ (y+radius)*size*size + z+radius] );
    }

    public static void marker(boolean[] markers, int radius, int size, int x, int y, int z){
        markers[(x+radius)*size+ (y+radius)*size*size + z+radius]=true;
    }

    public static final double SQRT2=Math.sqrt(2);

    public static Set<Entity> findCanHitEntity(LivingEntity creeper,int radius){
        BlockPos pos=creeper.blockPosition().above();
        BlockPos.MutableBlockPos testing=new BlockPos.MutableBlockPos();

        Map<BlockPos, ArrayList<Entity>> tryHitEntities=new HashMap<>();
        Set<Entity> canHitEntity=new HashSet<>();

        Iterable<Entity> entities= creeper.level().getEntities(null,new AABB(pos.offset(-radius,-radius,-radius),pos.offset(radius,radius,radius)));
        entities.forEach(entity -> tryHitEntities.compute(entity.blockPosition().above(),(k,o)->{
            if(o==null)
                o=new ArrayList<>(1);
            else
                o.ensureCapacity(16);
            o.add(entity);
            return o;
        }));
//        System.out.println(tryHitEntities.keySet());

        int maxDepth= (int) (radius*SQRT2);

        boolean[] markers;
        int markerSize=radius*2+1;
        markers=new boolean[markerSize*markerSize*markerSize];

        Queue<Integer> nodes=new LinkedList<>();
        nodes.add(0);

        Direction[] directions={Direction.WEST,Direction.SOUTH,Direction.DOWN,Direction.EAST,Direction.NORTH,Direction.UP};

        while(!nodes.isEmpty()){
            int element=nodes.poll();
            int depth=element>>24;
            if(depth>=maxDepth) continue;
            depth++;
            int x=(element<<24)>>24;
            int y=(element<<16)>>24;
            int z=(element<<8)>>24;

            testing.set(x,y,z).move(pos);
            tryHitEntities.computeIfPresent(testing,(k,v)->{
                canHitEntity.addAll(v);
                return v;
            });
            for(int i=0;i<6;i++){
                testing.set(x,y,z).move(directions[i]);
                if(canWalk(markers,radius,markerSize,testing.getX(),testing.getY(),testing.getZ())) {
                    BlockPos next= testing.offset(pos);
                    if(!creeper.level().getBlockState(next).isFaceSturdy(creeper.level(),next,directions[i].getOpposite())){
                        nodes.add((testing.getX()&0xff)|((testing.getY()&0xff)<<8)|((testing.getZ()&0xff)<<16)|(depth<<24));
                    }
                }
            }
            marker(markers,radius,markerSize,x,y,z);
        }

        /*for(int i=radius-1;i<markerSize;i++){
            System.out.println("============== "+(i-radius)+" ==============");
            for(int j=0;j<markerSize;j++){
                for(int k=0;k<markerSize;k++){
                    System.out.print(markers[j*markerSize +i*markerSize*markerSize + k]?'#':' ');
                }
                System.out.println();
            }
        }*/

        return canHitEntity;
    }
}
