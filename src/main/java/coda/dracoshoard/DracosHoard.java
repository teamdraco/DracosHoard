package coda.dracoshoard;

import coda.dracoshoard.client.ClientSetup;
import coda.dracoshoard.client.WeeRenderLayer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DracosHoard.MOD_ID)
@Mod.EventBusSubscriber(modid = DracosHoard.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DracosHoard {
    public static final String MOD_ID = "dracoshoard";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public DracosHoard() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        WeeRenderLayer.addUUIDs();
    }

    private void clientSetup(FMLClientSetupEvent event) {
    }
}
