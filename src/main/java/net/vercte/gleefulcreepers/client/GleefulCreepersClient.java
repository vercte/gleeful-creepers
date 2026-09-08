package net.vercte.gleefulcreepers.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.gleeper.GleeperModel;
import net.vercte.gleefulcreepers.gleeper.GleeperRenderer;

public class GleefulCreepersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(GleefulCreepers.GLEEPER, GleeperRenderer.getProvider());
        EntityModelLayerRegistry.registerModelLayer(GleeperRenderer.LAYER, GleeperModel::createBodyLayer);
    }
}
