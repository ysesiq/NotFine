package jss.notfine.util;

import net.minecraft.IBlockAccess;

public interface ILeafBlock {

    default boolean isFullLeaf(IBlockAccess world, int x, int y, int z) {
        return true;
    }

}
