package coda.weecore.client;

import coda.weecore.WeeCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

public class ClientSetup {

    public static void clientSetup() {
        PlayerRenderer managerDefault = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default");
        PlayerRenderer managerSlim = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim");

        managerDefault.addLayer(new WeeRenderLayer<>(managerDefault));
        managerSlim.addLayer(new WeeRenderLayer<>(managerSlim));
    }
}
