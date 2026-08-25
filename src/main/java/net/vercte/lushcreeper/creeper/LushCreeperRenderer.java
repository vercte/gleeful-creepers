package net.vercte.lushcreeper.creeper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import net.vercte.lushcreeper.LushCreeperMod;
import org.jetbrains.annotations.NotNull;

public class LushCreeperRenderer extends MobRenderer<LushCreeper, LushCreeperModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(LushCreeperMod.at("lush_creeper"), "main");

    public LushCreeperRenderer(EntityRendererProvider.Context context, LushCreeperModel model) {
        super(context, model, 0.5f);
    }

    public static EntityRendererProvider<LushCreeper> getProvider() {
        return m -> new LushCreeperRenderer(m, new LushCreeperModel(
                m.bakeLayer(LAYER)
        ));
    }

    protected void scale(LushCreeper creeper, PoseStack pose, float dt) {
        float swelling = creeper.getSwelling(dt);
        float pulse = 1.0F + Mth.sin(swelling * 100.0F) * swelling * 0.01F;
        swelling = Mth.clamp(swelling, 0.0F, 1.0F);
        swelling *= swelling;
        swelling *= swelling;
        float f2 = (1.0F + swelling * 0.4F) * pulse;
        float f3 = (1.0F + swelling * 0.1F) / pulse;
        pose.scale(f2, f3, f2);
    }

    protected float getWhiteOverlayProgress(LushCreeper creeper, float dt) {
        float swelling = creeper.getSwelling(dt);
        return (int)(swelling * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swelling, 0.5F, 1.0F);
    }

    @Override
    protected float getShadowRadius(LushCreeper p_316170_) {
        return super.getShadowRadius(p_316170_);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull LushCreeper lushCreeper) {
        return LushCreeperMod.at("textures/entity/lush_creeper.png");
    }
}
