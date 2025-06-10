package com.example.penic;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;

public abstract class CustomEnchantment {

    public abstract int getMaxLevel();
    public int getMinPower(int level) { return 1 + (level - 1) * 10; }
    public int getMaxPower(int level) { return getMinPower(level) + 5; }
    public boolean isCursed() { return false; }
    public boolean isTreasure() { return false; }
    public boolean conflictsWith(Enchantment other) { return false; }
}
