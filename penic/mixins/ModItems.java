package com.example.penic.mixins;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item SOUL_SHARD = new Item(new Item.Settings());

    public static void register() {
        Registry.register(Registries.ITEM, Identifier.of("penic", "soul_shard"), SOUL_SHARD);
    }
}
