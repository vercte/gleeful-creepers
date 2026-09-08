package net.vercte.gleefulcreepers.gleeper;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GleeperModel extends EntityModel<GleeperRenderState> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftHindLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart blossomStem;
	private final ModelPart blossom;

	public GleeperModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leftHindLeg = root.getChild("left_hind_leg");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.blossomStem = this.head.getChild("blossom_stem");
		this.blossom = this.head.getChild("blossom");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 3.0F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition rightHindLeg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 18.0F, 4.0F));
		PartDefinition leftHindLeg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 18.0F, 4.0F));
		PartDefinition rightFrontLeg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 18.0F, -4.0F));
		PartDefinition leftFrontLeg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 18.0F, -4.0F));

		PartDefinition blossomStem = head.addOrReplaceChild("blossom_stem", CubeListBuilder.create().texOffs(32, 0).addBox(-2.75F, -4.0F, 1.5F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 3.5F, 0.3698F, 0.1289F, -0.176F));

		PartDefinition blossom = head.addOrReplaceChild("blossom", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.5F, 3.25F, 0.0F, 0.0F, 0.1745F));
		blossom.addOrReplaceChild("blossom_right", CubeListBuilder.create().texOffs(32, 6).addBox(-5.5561F, -9.7255F, -0.0566F, 9.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, -0.1745F, 0.3927F, -1.5708F));
		blossom.addOrReplaceChild("blossom_bottom", CubeListBuilder.create().texOffs(32, 6).addBox(-4.2605F, -11.5145F, -0.372F, 9.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, -0.5672F, 0.0F, -3.1416F));
		blossom.addOrReplaceChild("blossom_left", CubeListBuilder.create().texOffs(32, 6).addBox(-2.4439F, -10.2386F, -0.147F, 9.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, -0.1745F, -0.3927F, 1.5708F));
		blossom.addOrReplaceChild("blossom_top", CubeListBuilder.create().texOffs(32, 6).addBox(-3.7395F, -8.4496F, 0.1684F, 9.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition heart = body.addOrReplaceChild("heart", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, 6.0F, -2.0F, 0.0F, 0.0F, 0.3491F));
		heart.addOrReplaceChild("left_r2", CubeListBuilder.create().texOffs(40, 25).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, -1.5708F));
		heart.addOrReplaceChild("bottom_r2", CubeListBuilder.create().texOffs(40, 25).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, -3.1416F));
		heart.addOrReplaceChild("right_r2", CubeListBuilder.create().texOffs(40, 25).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 1.5708F));
		heart.addOrReplaceChild("top_r2", CubeListBuilder.create().texOffs(40, 25).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(mesh, 64, 32);
	}

	@Override
	public void setupAnim(GleeperRenderState state) {
		this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
		this.head.xRot = state.xRot * (float) (Math.PI / 180.0);

		float animationSpeed = state.walkAnimationSpeed;
		float animationPos = state.walkAnimationPos;
		this.rightHindLeg.xRot = Mth.cos(animationPos * 0.6662F) * 1.4F * animationSpeed;
		this.leftHindLeg.xRot = Mth.cos(animationPos * 0.6662F + (float) Math.PI) * 1.4F * animationSpeed;
		this.rightFrontLeg.xRot = Mth.cos(animationPos * 0.6662F + (float) Math.PI) * 1.4F * animationSpeed;
		this.leftFrontLeg.xRot = Mth.cos(animationPos * 0.6662F) * 1.4F * animationSpeed;

		this.blossomStem.visible = !state.sheared;
		this.blossom.visible = !state.sheared;
	}
}