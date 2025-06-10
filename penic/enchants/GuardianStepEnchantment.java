package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.util.Rarity;

public class GuardianStepEnchantment extends CustomEnchantment {
    public GuardianStepEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_LEGS);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 15 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
