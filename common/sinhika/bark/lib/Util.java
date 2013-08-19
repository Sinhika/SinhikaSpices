package sinhika.bark.lib;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;

import com.google.common.base.Optional;

public class Util
{
    public static boolean isVanillaBlockID(int id)
    {
        return (id <= 256);
    }
    
    public static Optional<Item> getToolItem(EnumToolMaterial toolMaterial)
    {
        Optional<Item> no_item = Optional.absent();
        int id = toolMaterial.getToolCraftingMaterial();
        if (Util.isVanillaBlockID(id))
        {
            return no_item;
        }
        else {
            return Optional.of(Item.itemsList[id]);
        }
    } // end getToolItem
    
    public static Optional<Block> getToolBlock(EnumToolMaterial toolMaterial)
    {
        Optional<Block> no_block = Optional.absent();
        int id = toolMaterial.getToolCraftingMaterial();
        if (Util.isVanillaBlockID(id))
        {
            return Optional.of(Block.blocksList[id]);
        }
        else {
            return no_block;
        }     
    } // end getToolBlock
}
