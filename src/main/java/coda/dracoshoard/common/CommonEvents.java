package coda.dracoshoard.common;

import coda.dracoshoard.DracosHoard;
import coda.dracoshoard.client.ClientEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Mod.EventBusSubscriber(modid = DracosHoard.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void reloadListener(AddReloadListenerEvent event) {
        ClientEvents.clientSetup();
    }

    public static boolean isSupporter(Player player) {
        InputStream in;

        try {
            if (!player.level.isClientSide) {
                in = new URL("https://github.com/teamdraco/DracosHoard/blob/master/supporters.txt").openStream();
                List<String> contents = IOUtils.readLines(in, StandardCharsets.UTF_8);
                for (String line : contents) {
                    if (player.getUUID().toString().replaceAll("-", "").equals(line.replaceAll("-", ""))) {
                        return true;
                    }
                }
            }
        } catch (IOException exception) {
        }
        return false;
    }

}
