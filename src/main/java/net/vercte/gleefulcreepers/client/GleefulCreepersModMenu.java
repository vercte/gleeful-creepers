package net.vercte.gleefulcreepers.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.vercte.gleefulcreepers.GleefulCreepers;

public class GleefulCreepersModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return p -> new ConfigurationScreen(GleefulCreepers.ID, p);
    }
}
