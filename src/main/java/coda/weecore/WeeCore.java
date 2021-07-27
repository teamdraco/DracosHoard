package coda.weecore;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WeeCore.MOD_ID)
@Mod.EventBusSubscriber(modid = WeeCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeeCore {

    public static final String MOD_ID = "weecore";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public WeeCore() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    }
}
