package coda.dracoshoard.client;

import coda.dracoshoard.client.renderer.RedDragonRenderLayer;
import coda.dracoshoard.client.renderer.WeeRenderLayer;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientSetup {
    public static final List<UUID> list = new ArrayList<>();

    public static void clientSetup() {
        var managerDefault = (PlayerRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default");
        var managerSlim = (PlayerRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim");

        managerDefault.addLayer(new WeeRenderLayer<>(managerDefault));
        managerSlim.addLayer(new WeeRenderLayer<>(managerSlim));
    }

    public static void addUUIDs() {
        list.add(UUID.fromString("526ab88d-f353-4421-9d18-a629f735ac47")); // Coda
        list.add(UUID.fromString("2d173722-de6b-4bb8-b21b-b2843cfe395d")); // Ninni
        list.add(UUID.fromString("cc14fbdd-6b9b-42b7-a171-564860591cc5")); // Zae
        list.add(UUID.fromString("c92ed179-b1eb-4254-a4f8-590beecd2b0d")); // Esuoh
        list.add(UUID.fromString("aca529a2-1166-41aa-b304-209f06831998")); // Tazz
        list.add(UUID.fromString("0c22615f-a189-4f4e-85ae-79fd80c353c8")); // Vaky
    }

    public static boolean isSupporter(Player entity) {
        return list.contains(entity.getUUID());
    }

    public static String modelToRender() {
        String uuid = list.get(0).toString();

        System.out.println("UUID = " + uuid);
        return uuid;
    }
}
