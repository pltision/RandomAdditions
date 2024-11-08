package yee.pltision.randomadditions;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import yee.pltision.randomadditions.block.CloudBlock;
import yee.pltision.randomadditions.block.CloudGlassBlock;
import yee.pltision.randomadditions.block.SaureaFrostBlock;
import yee.pltision.randomadditions.block.TreePlacerBlock;
import yee.pltision.randomadditions.effect.FreezeEffect;
import yee.pltision.randomadditions.entity.FreezeArrow;
import yee.pltision.randomadditions.entity.FreezeSkeleton;
import yee.pltision.randomadditions.item.projectile.FreezeArrowItem;

import java.util.function.Function;
import java.util.function.Supplier;

import static yee.pltision.randomadditions.PertazAdditions.BlockAndItem.of;

// The value here should match an entry in the META-INF/mods.toml file
@SuppressWarnings({"deprecation", "NullableProblems", "unused"})
@Mod(PertazAdditions.MODID)
public class PertazAdditions
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "pertazrandom";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);

    //Blocks
    public static final BlockAndItem<CloudBlock,?> CLOUD = of("cloud", () -> new CloudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).randomTicks().noOcclusion().isViewBlocking((a,b,c)->false).sound(SoundType.WOOL)));
    public static final BlockAndItem<CloudGlassBlock,?> GRASS_CLOUD = of("grass_cloud", () -> new CloudGlassBlock(BlockBehaviour.Properties.copy(CLOUD.block())));

    public static final BlockAndItem<HalfTransparentBlock, ?> CLOUDY_BRUSH = of("cloudy_brush", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).randomTicks().noOcclusion().isViewBlocking((a, b, c) -> false).sound(SoundType.GRASS)) {
        @Override
        public int getLightBlock(BlockState p_60585_, BlockGetter p_60586_, BlockPos p_60587_) {
            return 0;
        }
    });
    public static final BlockAndItem<HalfTransparentBlock, ?> CLOUDY_LEAVES = of("cloudy_leaves", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).randomTicks().noOcclusion().isViewBlocking((a, b, c) -> false).sound(SoundType.GRASS)) {
        @Override
        public int getLightBlock(BlockState p_60585_, BlockGetter p_60586_, BlockPos p_60587_) {
            return 0;
        }
    });
    public static final BlockAndItem<TreePlacerBlock,?> TREE_PLACER = of("tree_placer", () -> new TreePlacerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final BlockAndItem<SaureaFrostBlock,?> SAUREA_FROST = of("saurea_frost", () -> new SaureaFrostBlock(()->MobEffects.NIGHT_VISION,120,BlockBehaviour.Properties.copy(Blocks.POPPY)));

    //Items
    public static final RegistryObject<Item> FREEZE_ARROW_ITEM = ITEMS.register("freeze_arrow", () -> new FreezeArrowItem(new Item.Properties()));

    //Entities
    public static final RegistryObject<EntityType<FreezeArrow>> FREEZE_ARROW = entity("freeze_arrow",
            EntityType.Builder.<FreezeArrow>of(FreezeArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
    );
    public static final RegistryObject<EntityType<FreezeSkeleton>> FREEZE_SKELETON = entity("freeze_skeleton",
            EntityType.Builder.of(FreezeSkeleton::new, MobCategory.MONSTER).sized(0.6F, 1.99F).immuneTo(Blocks.POWDER_SNOW).clientTrackingRange(8)
    );

    public static <E extends Entity> RegistryObject<EntityType<E>> entity(String name,EntityType.Builder<E> builder){
        return ENTITY_TYPES.register(name,()->builder.build(location(name).toString()));
    }

    public static void registryEntityAttributes(EntityAttributeCreationEvent event){
        event.put(FREEZE_SKELETON.get(), AbstractSkeleton.createAttributes().build());
    }


    public static final RegistryObject<FreezeEffect> FREEZE_EFFECT = MOB_EFFECTS.register("freeze",()->new FreezeEffect(MobEffectCategory.HARMFUL,0x4080ff));

    public static final GameRules.Key<GameRules.IntegerValue> CLOUD_FLOW_HEIGHT = GameRules.register(MODID+".cloudFlowHeight", GameRules.Category.UPDATES, GameRules.IntegerValue.create(200));

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(()->GRASS_CLOUD.item().getDefaultInstance())
            .displayItems((parameters, output) -> {
            }).build());

    public PertazAdditions()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
//        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        MOB_EFFECTS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(PertazAdditions::registryEntityAttributes);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /*// Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }*/

    public static ResourceLocation location(String name){
        return new ResourceLocation(MODID,name);
    }

    public static class BlockAndItem<B extends Block,I extends BlockItem>{
        private final RegistryObject<B> block;  //private因为影响代码提示
        private final RegistryObject<I> item;
        public B block(){
            return block.get();
        }
        public I item(){
            return item.get();
        }
        public BlockAndItem(RegistryObject<B> b, RegistryObject<I> i){
            block=b;
            item=i;
        }
        public static <B extends Block> BlockAndItem<B,?> of(String id, Supplier<B> b){
            RegistryObject<B> block=BLOCKS.register(id,b);
            return new BlockAndItem<>(block,ITEMS.register(id,()->new BlockItem(block.get(),new Item.Properties())));
        }
        public static <B extends Block> BlockAndItem<B,?> of(String id, Supplier<B> b,Item.Properties i){
            RegistryObject<B> block=BLOCKS.register(id,b);
            return new BlockAndItem<>(block,ITEMS.register(id,()->new BlockItem(block.get(),i)));
        }
        public static <B extends Block,I extends BlockItem> BlockAndItem<B,I> of(String id, Supplier<B> b, Function<B,I> i){
            RegistryObject<B> block=BLOCKS.register(id,b);
            return new BlockAndItem<>(block,ITEMS.register(id,()->i.apply(block.get())));
        }

    }
}
