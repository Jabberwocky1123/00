package com.example.penic;

import com.example.penic.mixins.ModItems;
import com.example.penic.conflict.DesiccantCurseEnchantment;
import com.example.penic.conflict.ImmunityEnchantment;
import com.example.penic.enchants.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentInitializer implements ModInitializer {
        public static final Map<Identifier, CustomEnchantment> ENCHANTMENTS = new HashMap<>();

        @Override
        public void onInitialize() {
            register("explosive_mining", new ExplosiveMiningEnchantment(), EnchantmentTarget.DIGGER);
            register("drill", new DrillEnchantment(), EnchantmentTarget.DIGGER);
            register("wisdom", new WisdomEnchantment(), EnchantmentTarget.WEAPON);
            register("desiccant_curse", new DesiccantCurseEnchantment(), EnchantmentTarget.WEAPON);
            register("vampirism", new VampirismEnchantment(), EnchantmentTarget.WEAPON);
            register("wind_strike", new WindStrikeEnchantment(), EnchantmentTarget.WEAPON);
            register("tree_feller", new TreeFellerEnchantment(), EnchantmentTarget.AXE);
            register("photosynthesis", new PhotosynthesisEnchantment(), EnchantmentTarget.ARMOR_HEAD);
            register("acceleration", new AccelerationEnchantment(), EnchantmentTarget.ARMOR_CHEST);
            register("immunity", new ImmunityEnchantment(), EnchantmentTarget.ARMOR_CHEST);
            register("leap", new LeapEnchantment(), EnchantmentTarget.ARMOR_LEGS);
            register("lava_walker", new LavawalkerEnchantment(), EnchantmentTarget.ARMOR_FEET);
        }

        private void register(String name, CustomEnchantment enchant, EnchantmentTarget target) {
            Identifier id = new Identifier("yourmod", name);
            ENCHANTMENTS.put(id, enchant);
            Registry.register(Registries.ENCHANTMENT, id, new EnchantmentWrapper(enchant, target));
        }
    }
