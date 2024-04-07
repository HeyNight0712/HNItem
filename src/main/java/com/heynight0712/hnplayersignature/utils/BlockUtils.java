package com.heynight0712.hnplayersignature.utils;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockUtils {
    public static ItemMeta setCustomName(Block block) {
        if (block.getState() instanceof TileState) {
            TileState tileState = (TileState) block.getState();
        }
        return null;
    }
}
