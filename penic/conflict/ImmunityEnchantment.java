package com.example.penic.conflict;

import com.example.penic.CustomEnchantment;
import net.minecraft.enchantment.Enchantment;
import com.example.penic.EnchantmentInitializer;

public class ImmunityEnchantment extends CustomEnchantment {
    @Override public boolean conflictsWith(Enchantment other) {
        return other == EnchantmentInitializer.BEES_FURY;
    }
}
