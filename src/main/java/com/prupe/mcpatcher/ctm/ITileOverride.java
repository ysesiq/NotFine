package com.prupe.mcpatcher.ctm;

import java.util.List;
import java.util.Set;

import net.minecraft.Icon;

import com.prupe.mcpatcher.mal.block.BlockStateMatcher;

interface ITileOverride extends Comparable<ITileOverride> {

    boolean isDisabled();

    void registerIcons();

    List<BlockStateMatcher> getMatchingBlocks();

    Set<String> getMatchingTiles();

    int getRenderPass();

    int getWeight();

    Icon getTileWorld(RenderBlockState renderBlockState, Icon origIcon);

    Icon getTileHeld(RenderBlockState renderBlockState, Icon origIcon);
}
