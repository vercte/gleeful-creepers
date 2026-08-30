package net.vercte.gleefulcreepers.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.neoforged.fml.common.Mod;
import net.vercte.gleefulcreepers.gleeper.GleeperModel;
import net.vercte.gleefulcreepers.gleeper.GleeperRenderer;

@Mod(GleefulCreepers.ID)
public class GleefulCreepersClient {
    public GleefulCreepersClient(IEventBus bus, ModContainer container) {
        bus.addListener(this::setup);

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void setup(FMLClientSetupEvent event) {
        EntityRenderers.register(GleefulCreepers.GLEEPER.get(), GleeperRenderer.getProvider());
        ClientHooks.registerLayerDefinition(GleeperRenderer.LAYER, GleeperModel::createBodyLayer);
    }
}
