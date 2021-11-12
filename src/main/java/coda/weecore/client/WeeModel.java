package coda.weecore.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class WeeModel<T extends Entity> extends EntityModel<T> {
    public final ModelRenderer bone;
    public final ModelRenderer bone2;
    public final ModelRenderer bone3;
    public final ModelRenderer bone4;

    public WeeModel() {
        texWidth = 32;
        texHeight = 16;

        bone = new ModelRenderer(this);
        bone.setPos(0.0F, 22.0F, 0.0F);
        bone.texOffs(0, 3).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 4.0F, 6.0F, 0.0F, false);
        bone.texOffs(0, 0).addBox(0.0F, -3.0F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setPos(0.0F, 0.0F, 3.0F);
        bone.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 0.0F, 0.0F);
        bone2.texOffs(0, 0).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setPos(1.5F, 1.5F, 0.5F);
        bone.addChild(bone3);
        setRotationAngle(bone3, 0.0F, 0.0F, -0.7854F);
        bone3.texOffs(0, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setPos(-1.5F, 1.5F, 0.5F);
        bone.addChild(bone4);
        setRotationAngle(bone4, 0.0F, 0.0F, 0.7854F);
        bone4.texOffs(1, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 3.0f;
        this.bone.yRot = MathHelper.cos(ageInTicks * speed * 0.4F) * 0.5F * 0.5F * f1;
        this.bone2.yRot = MathHelper.cos(ageInTicks * speed * 0.4F) * 0.5F * -0.5F * f1;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}