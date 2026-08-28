package net.vercte.gleefulcreepers;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public class GleefulTags {
    public static final TagKey<Item> ACTS_AS_BONE_MEAL = TagKey.create(Registries.ITEM, GleefulCreepers.at("acts_as_bone_meal"));
    public static final TagKey<EntityType<?>> GLEEPER_FORGIVES = TagKey.create(Registries.ENTITY_TYPE, GleefulCreepers.at("gleeper_forgives"));
    public static final TagKey<Biome> GLEEPER_SPAWNS_IN = TagKey.create(Registries.BIOME, GleefulCreepers.at("gleeper_spawns_in"));
}
