package coda.weecore.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;

public class ClientShenanigans {

    public static void clientSetup() {
        PlayerRenderer managerDefault = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default");
        PlayerRenderer managerSlim = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim");
        managerDefault.addLayer(new WeeRenderLayer<>(managerDefault));
        managerSlim.addLayer(new WeeRenderLayer<>(managerSlim));
    }
}
