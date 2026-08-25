package net.vercte.lushcreeper.creeper;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.vercte.lushcreeper.LushCreeperMod;
import org.jetbrains.annotations.NotNull;

public class LushCreeperRenderer extends MobRenderer<LushCreeper, LushCreeperModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(LushCreeperMod.at("lush_creeper"), "main");

    public LushCreeperRenderer(EntityRendererProvider.Context context, LushCreeperModel model) {
        super(context, model, 1.25f);
    }

    public static EntityRendererProvider<LushCreeper> getProvider() {
        return m -> new LushCreeperRenderer(m, new LushCreeperModel(
                m.bakeLayer(LAYER)
        ));
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull LushCreeper lushCreeper) {
        return LushCreeperMod.at("textures/entity/lush_creeper.png");
    }
}
