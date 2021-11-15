package coda.dracoshoard.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class WeeModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart bone;
    public final ModelPart bone2;
    public final ModelPart bone3;
    public final ModelPart bone4;

    public WeeModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.bone2 = this.bone.getChild("bone2");
        this.bone3 = this.bone.getChild("bone3");
        this.bone4 = this.bone.getChild("bone4");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 3).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 4.0F, 6.0F, false).texOffs(0, 0).addBox(0.0F, -3.0F, 0.0F, 0.0F, 1.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.5F, 1.5F, 0.5F, 0.0F, 0.0F, -0.7854F));
        PartDefinition bone4 = bone.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(1, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-1.5F, 1.5F, 0.5F, 0.0F, 0.0F, 0.7854F));
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(T entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 3.0f;
        this.bone.yRot = Mth.cos(ageInTicks * speed * 0.4F) * 0.5F * 0.5F * f1;
        this.bone2.yRot = Mth.cos(ageInTicks * speed * 0.4F) * 0.5F * -0.5F * f1;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}