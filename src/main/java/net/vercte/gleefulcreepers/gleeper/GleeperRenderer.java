package net.vercte.gleefulcreepers.gleeper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.vercte.gleefulcreepers.GleefulCreepers;
import org.jetbrains.annotations.NotNull;

public class GleeperRenderer extends MobRenderer<Gleeper, GleeperRenderState, GleeperModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(GleefulCreepers.at("gleeper"), "main");

    private static final Identifier HAPPY_TEXTURE = GleefulCreepers.at("textures/entity/gleeper/happy.png");
    private static final Identifier NEUTRAL_TEXTURE = GleefulCreepers.at("textures/entity/gleeper/neutral.png");
    private static final Identifier ANGERED_TEXTURE = GleefulCreepers.at("textures/entity/gleeper/angered.png");

    public GleeperRenderer(EntityRendererProvider.Context context, GleeperModel model) {
        super(context, model, 0.5f);
    }

    public static EntityRendererProvider<Gleeper> getProvider() {
        return m -> new GleeperRenderer(m, new GleeperModel(
                m.bakeLayer(LAYER)
        ));
    }

    @Override
    @NotNull
    public GleeperRenderState createRenderState() {
        return new GleeperRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Gleeper entity, @NotNull GleeperRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.swelling = entity.getSwelling(partialTicks);
        state.sheared = entity.isSheared();
        state.siezed = entity.isSiezed();
        state.angered = entity.isAngered();
        state.isHurt = entity.hurtTime > 0;
    }

    protected void scale(GleeperRenderState state, PoseStack pose) {
        float swelling = state.swelling;
        float pulse = 1.0F + Mth.sin(swelling * 100.0F) * swelling * 0.01F;
        swelling = Mth.clamp(swelling, 0.0F, 1.0F);
        swelling *= swelling;
        swelling *= swelling;
        float f2 = (1.0F + swelling * 0.4F) * pulse;
        float f3 = (1.0F + swelling * 0.1F) / pulse;
        pose.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(GleeperRenderState state) {
        float swelling = state.swelling;
        return (int)(swelling * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swelling, 0.5F, 1.0F);
    }

    @Override
    protected boolean isShaking(@NotNull GleeperRenderState state) {
        return super.isShaking(state) || state.siezed;
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull GleeperRenderState state) {
        if(state.siezed) return NEUTRAL_TEXTURE;
        if(state.angered) return ANGERED_TEXTURE;
        if(state.isHurt || state.sheared) return NEUTRAL_TEXTURE;
        return HAPPY_TEXTURE;
    }
}
