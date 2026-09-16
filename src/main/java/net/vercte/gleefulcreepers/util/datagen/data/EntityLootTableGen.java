package net.vercte.gleefulcreepers.util.datagen.data;

import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.vercte.gleefulcreepers.GleefulCreepers;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class EntityLootTableGen extends EntityLootSubProvider {
    protected EntityLootTableGen(LootTableSubProvider.Context output) {
        super(FeatureFlags.REGISTRY.allFlags(), output);
    }

    @Override
    @NotNull
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(GleefulCreepers.GLEEPER.get());
    }

    @Override
    public void generate() {
        HolderGetter<EntityType<?>> entities = output.lookup(Registries.ENTITY_TYPE);

        add(
                GleefulCreepers.GLEEPER.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.GUNPOWDER)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.MOSS_BLOCK)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .add(TagEntry.expandTag(items.getOrThrow(ItemTags.CREEPER_DROP_MUSIC_DISCS)))
                                        .when(
                                                LootItemEntityPropertyCondition.hasProperties(
                                                        LootContext.EntityTarget.ATTACKER, EntityPredicate.Builder.entity().of(entities, EntityTypeTags.SKELETONS)
                                                )
                                        )
                        )
        );
    }
}
