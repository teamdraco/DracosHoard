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
import net.minecraft.world.entity.Entity;

public class RedDragonModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart body;
    public final ModelPart tail;
    public final ModelPart rightWing;
    public final ModelPart leftWing;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    public RedDragonModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.rightWing = this.body.getChild("rightWing");
        this.leftWing = this.body.getChild("leftWing");
        this.rightLeg = this.body.getChild("rightLeg");
        this.leftLeg = this.body.getChild("leftLeg");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 23.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 8).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -0.5F, 3.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(-5, 8).addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 5.0F, false), PartPose.offsetAndRotation(-1.5F, -1.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(-5, 13).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 5.0F, false), PartPose.offsetAndRotation(1.5F, -1.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, false), PartPose.offsetAndRotation(-1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(T entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}