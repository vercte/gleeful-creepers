package net.vercte.gleefulcreepers.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.gleeper.GleeperModel;
import net.vercte.gleefulcreepers.gleeper.GleeperRenderer;

public class GleefulCreepersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.register(GleefulCreepers.GLEEPER, GleeperRenderer.getProvider());
        ModelLayerRegistry.registerModelLayer(GleeperRenderer.LAYER, GleeperModel::createBodyLayer);
    }
}
