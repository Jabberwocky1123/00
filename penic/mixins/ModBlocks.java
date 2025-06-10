package com.example.penic.mixins;

import com.jcraft.jorbis.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block GLOW_MARKER = new Block(AbstractBlock.Settings.create()
            .strength(0.5f)
            .luminance(15)
            .nonOpaque());

    public static void register() {
        Registry.register(Registries.BLOCK, new Identifier("penic", "glow_marker"), GLOW_MARKER);
    }
}
