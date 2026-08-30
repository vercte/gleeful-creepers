package net.vercte.gleefulcreepers.gleeper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.vercte.gleefulcreepers.GleefulCreepers;
import org.jetbrains.annotations.NotNull;

public class GleeperRenderer extends MobRenderer<Gleeper, GleeperModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(GleefulCreepers.at("gleeper"), "main");

    private static final ResourceLocation HAPPY_TEXTURE = GleefulCreepers.at("textures/entity/gleeper/happy.png");
    private static final ResourceLocation NEUTRAL_TEXTURE = GleefulCreepers.at("textures/entity/gleeper/neutral.png");
    private static final ResourceLocation ANGERED_TEXTURE = GleefulCreepers.at("textures/entity/gleeper/angered.png");

    public GleeperRenderer(EntityRendererProvider.Context context, GleeperModel model) {
        super(context, model, 0.5f);
    }

    public static EntityRendererProvider<Gleeper> getProvider() {
        return m -> new GleeperRenderer(m, new GleeperModel(
                m.bakeLayer(LAYER)
        ));
    }

    protected void scale(Gleeper creeper, PoseStack pose, float dt) {
        float swelling = creeper.getSwelling(dt);
        float pulse = 1.0F + Mth.sin(swelling * 100.0F) * swelling * 0.01F;
        swelling = Mth.clamp(swelling, 0.0F, 1.0F);
        swelling *= swelling;
        swelling *= swelling;
        float f2 = (1.0F + swelling * 0.4F) * pulse;
        float f3 = (1.0F + swelling * 0.1F) / pulse;
        pose.scale(f2, f3, f2);
    }

    protected float getWhiteOverlayProgress(Gleeper creeper, float dt) {
        float swelling = creeper.getSwelling(dt);
        return (int)(swelling * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swelling, 0.5F, 1.0F);
    }

    @Override
    protected boolean isShaking(@NotNull Gleeper creeper) {
        return super.isShaking(creeper) || creeper.isSiezed();
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull Gleeper lushCreeper) {
        if(lushCreeper.isSiezed()) return NEUTRAL_TEXTURE;
        if(lushCreeper.isAngered()) return ANGERED_TEXTURE;
        if(lushCreeper.hurtTime > 0 || lushCreeper.isSheared()) return NEUTRAL_TEXTURE;
        return HAPPY_TEXTURE;
    }
}
