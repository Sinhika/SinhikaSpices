package sinhika.spices;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import sinhika.spices.lib.BarkHelper;

public class BarkItemBlock extends ItemBlockWithMetadata
{

    public BarkItemBlock(int par1, Block par2Block)
    {
        super(par1, par2Block);
        setCreativeTab(Spices.customTabSpices);
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an
     * ItemStack so different stacks can have different names based on their
     * damage or NBT.
     */
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        // remove 'tile:' prefix, prefix the barktype, replace "tile:"...
        return "tile:" + BarkHelper.getTypeName(par1ItemStack.getItemDamage())
                + "_" + super.getUnlocalizedName(par1ItemStack).substring(5);
    }
}
