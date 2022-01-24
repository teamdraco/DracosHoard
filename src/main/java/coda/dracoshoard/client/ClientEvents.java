package coda.dracoshoard.client;

import coda.dracoshoard.client.renderer.WeeRenderLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public class ClientEvents {

    public static void clientSetup() {

        var managerDefault = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default");
        var managerSlim = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim");

        managerDefault.addLayer(new WeeRenderLayer<>(managerDefault));
        managerSlim.addLayer(new WeeRenderLayer<>(managerSlim));
    }
}