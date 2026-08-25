package net.vercte.gleefulcreepers.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ClientHooks;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.neoforged.fml.common.Mod;
import net.vercte.gleefulcreepers.creeper.GleeperModel;
import net.vercte.gleefulcreepers.creeper.GleeperRenderer;

@Mod(GleefulCreepers.ID)
public class GleefulCreepersClient {
    public GleefulCreepersClient(IEventBus bus) {
        bus.addListener(this::setup);
    }

    private void setup(FMLClientSetupEvent event) {
        EntityRenderers.register(GleefulCreepers.GLEEPER.get(), GleeperRenderer.getProvider());
        ClientHooks.registerLayerDefinition(GleeperRenderer.LAYER, GleeperModel::createBodyLayer);
    }
}
