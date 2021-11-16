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

public class BlueDragonModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart body;
    public final ModelPart tail;
    public final ModelPart horns;
    public final ModelPart leftFrontFlipper;
    public final ModelPart leftBackFlipper;
    public final ModelPart rightFrontFlipper;
    public final ModelPart rightBackFlipper;

    public BlueDragonModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.horns = this.body.getChild("horns");
        this.leftFrontFlipper = this.body.getChild("leftFrontFlipper");
        this.rightFrontFlipper = this.body.getChild("rightFrontFlipper");
        this.rightBackFlipper = this.body.getChild("rightBackFlipper");
        this.leftBackFlipper = this.body.getChild("leftBackFlipper");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 7.0F, false), PartPose.offsetAndRotation(0.0F, 23.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 9).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, 3.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition horns = body.addOrReplaceChild("horns", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, -1.0F, -2.5F, -0.7854F, 0.0F, 0.0F));
        PartDefinition leftFrontFlipper = body.addOrReplaceChild("leftFrontFlipper", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, false), PartPose.offsetAndRotation(1.0F, 1.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition rightFrontFlipper = body.addOrReplaceChild("rightFrontFlipper", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, false), PartPose.offsetAndRotation(-1.0F, 1.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition rightBackFlipper = body.addOrReplaceChild("rightBackFlipper", CubeListBuilder.create().texOffs(4, 0).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, false), PartPose.offsetAndRotation(-1.0F, 1.0F, 2.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition leftBackFlipper = body.addOrReplaceChild("leftBackFlipper", CubeListBuilder.create().texOffs(4, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, false), PartPose.offsetAndRotation(1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(T entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}