package net.vercte.lushcreeper.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ClientHooks;
import net.vercte.lushcreeper.LushCreeperMod;
import net.neoforged.fml.common.Mod;
import net.vercte.lushcreeper.creeper.LushCreeperModel;
import net.vercte.lushcreeper.creeper.LushCreeperRenderer;

@Mod(LushCreeperMod.ID)
public class LushCreeperModClient {
    public LushCreeperModClient(IEventBus bus) {
        bus.addListener(this::setup);
    }

    private void setup(FMLClientSetupEvent event) {
        EntityRenderers.register(LushCreeperMod.LUSH_CREEPER.get(), LushCreeperRenderer.getProvider());
        ClientHooks.registerLayerDefinition(LushCreeperRenderer.LAYER, LushCreeperModel::createBodyLayer);
    }
}
