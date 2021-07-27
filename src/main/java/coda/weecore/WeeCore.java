package coda.weecore;

import coda.weecore.common.entities.companion.DragonCompanionEntity;
import coda.weecore.init.WCEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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

        WCEntities.REGISTER.register(bus);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(WCEntities.DRAGON_COMPANION.get(), DragonCompanionEntity.createAttributes().build());
    }
}
