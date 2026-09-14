package net.vercte.gleefulcreepers.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.gleeper.GleeperModel;
import net.vercte.gleefulcreepers.gleeper.GleeperRenderer;

public class GleefulCreepersClient {
    public static void setup(FMLClientSetupEvent event) {
        EntityRenderers.register(GleefulCreepers.GLEEPER.get(), GleeperRenderer.getProvider());
    }

    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GleeperRenderer.LAYER, GleeperModel::createBodyLayer);
    }
}
