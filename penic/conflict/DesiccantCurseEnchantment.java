package com.example.penic.conflict;

import com.example.penic.CustomEnchantment;
import com.example.penic.EnchantmentInitializer;
import net.minecraft.enchantment.Enchantment;

public class DesiccantCurseEnchantment extends CustomEnchantment {
    @Override public boolean conflictsWith(Enchantment other) {
        return other == EnchantmentInitializer.VAMPIRISM;
    }
}
