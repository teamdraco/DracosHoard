package coda.dracoshoard;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(DracosHoard.MOD_ID)
@Mod.EventBusSubscriber(modid = DracosHoard.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DracosHoard {
    public static final String MOD_ID = "dracoshoard";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public DracosHoard() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        GeckoLib.initialize();
    }

}
