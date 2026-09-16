package net.vercte.gleefulcreepers.util.datagen.data;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class LootGen extends LootTableProvider {
    public LootGen() {
        super(
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(EntityLootTableGen::new, LootContextParamSets.ENTITY))
        );
    }
}
