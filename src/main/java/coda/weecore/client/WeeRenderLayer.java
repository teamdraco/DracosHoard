package coda.weecore.client;

import coda.weecore.WeeCore;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WeeRenderLayer<T extends PlayerEntity> extends LayerRenderer<T, PlayerModel<T>> {
    private final WeeModel<Entity> model = new WeeModel<>();
    public static final ResourceLocation TEXTURE = new ResourceLocation(WeeCore.MOD_ID, "textures/wee/wee_1.png");

    public WeeRenderLayer(IEntityRenderer<T, PlayerModel<T>> p_i50929_1_) {
        super(p_i50929_1_);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        this.render(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_);
    }

    private void render(MatrixStack ms, IRenderTypeBuffer buffer, int p_229136_3_, T entity, float p_225628_5_) {
        ms.pushPose();

        float rotation = entity.tickCount % 360;

        float x = MathHelper.sin(rotation * 0.15F);
        float y = MathHelper.sin(rotation * 0.1F) * 0.5F;
        float z = MathHelper.cos(rotation * 0.15F);

        ms.translate(x, y - 1, z);

        ms.mulPose(Vector3f.YP.rotation((float) ((rotation * 0.15f) + Math.toRadians(270))));

        IVertexBuilder ivertexbuilder = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.bone.render(ms, ivertexbuilder, p_229136_3_, OverlayTexture.NO_OVERLAY);
        ms.popPose();
    }
}
