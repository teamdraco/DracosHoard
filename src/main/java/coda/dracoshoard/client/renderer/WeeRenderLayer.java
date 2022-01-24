package coda.dracoshoard.client.renderer;

import coda.dracoshoard.DracosHoard;
import coda.dracoshoard.client.models.WeeModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WeeRenderLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
    private final WeeModel<Entity> model = new WeeModel<>(WeeModel.createLayerDefinition().bakeRoot());
    public static final ResourceLocation TEXTURE = new ResourceLocation(DracosHoard.MOD_ID, "textures/wee_1.png");

    public WeeRenderLayer(RenderLayerParent<T, PlayerModel<T>> layerParent) {
        super(layerParent);
    }

    public void render(PoseStack ms, MultiBufferSource buffer, int p_225628_3_, T entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        this.render(ms, buffer, p_225628_3_, entity);
    }

    private void render(PoseStack ms, MultiBufferSource buffer, int p_229136_3_, T entity) {
        ms.pushPose();

        float rotation = entity.tickCount % 360;

        float x = Mth.sin(rotation * 0.15F);
        float y = Mth.sin(rotation * 0.1F) * 0.5F;
        float z = Mth.cos(rotation * 0.15F);

        ms.translate(x, y - 1, z);

        ms.mulPose(Vector3f.YP.rotation((float) ((rotation * 0.15f) + Math.toRadians(270))));

        VertexConsumer ivertexbuilder = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.bone.render(ms, ivertexbuilder, p_229136_3_, OverlayTexture.NO_OVERLAY);
        ms.popPose();
    }
}
