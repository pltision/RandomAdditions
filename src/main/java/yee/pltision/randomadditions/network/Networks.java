package yee.pltision.randomadditions.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import yee.pltision.randomadditions.PertazAdditions;

public class Networks {
    private static final String PROTOCOL_VERSION = "0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            PertazAdditions.location("network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register(){
        int i=0;
        INSTANCE.registerMessage(i, CCreeperFireworkExplode.class, CCreeperFireworkExplode::encode,CCreeperFireworkExplode::new, CCreeperFireworkExplode::handle);
    }
}
