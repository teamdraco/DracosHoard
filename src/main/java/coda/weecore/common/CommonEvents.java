package coda.weecore.common;

import coda.weecore.WeeCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeeCore.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void summonCompanion(PlayerEvent.PlayerLoggedInEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
        }
    }

    private static boolean isSupporter(PlayerEntity player) {
        UUID uuid = player.getUUID();

        return uuid.toString().equals("526ab88df35344219d18a629f735ac47");
    }
}
