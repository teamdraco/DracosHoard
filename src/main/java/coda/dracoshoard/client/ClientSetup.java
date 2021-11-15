package coda.dracoshoard.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;

public class ClientSetup {

    public void clientSetup() {
    //   EntityRenderer<? extends Player> managerDefault = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default");


        PlayerRenderer managerDefault = (PlayerRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(Minecraft.getInstance().player);
        PlayerRenderer managerSlim = (PlayerRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim");


        managerDefault.addLayer(new WeeRenderLayer<>(managerDefault));
        managerSlim.addLayer(new WeeRenderLayer<>(managerSlim));
    }
}
