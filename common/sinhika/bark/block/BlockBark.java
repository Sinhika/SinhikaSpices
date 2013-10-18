package sinhika.bark.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import sinhika.bark.Bark;
import sinhika.bark.handlers.BarkHelper;
import sinhika.bark.handlers.BlockHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBark extends Block
{
    /** The type of tree this block came from. */
    private static int ANDLIMIT = 3; // change if number of Bark.barkTypes
                                     // changes.

    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public BlockBark(int par1)
    {
        super(par1, Material.wood);
        setCreativeTab(Bark.customTabSpices);
        setStepSound(Block.soundWoodFootstep);
        setHardness(1.5F);
        setUnlocalizedName("barkBlock");
        setTextureName("log");
        BlockBark.setBurnProperties(this.blockID, 5, 5);
     }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        if (par2 < 0 || par2 >= iconArray.length)
        {
            par2 = 0;
        }
        return iconArray[par2];
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and
     * wood.
     */
    @Override
    public int damageDropped(int par1)
    {
        return par1;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: bark
     * returns 4 blocks)
     */
    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs,
            @SuppressWarnings("rawtypes") List subItems)
    {
        for (int i = 0; i < BlockHelper.INSTANCE.size(); i++)
        {
            int meta = BlockHelper.INSTANCE.getMetadata(i);
            subItems.add(new ItemStack(par1, 1, meta));
        }
    } // end getSubBlocks()

    /*
     * (non-Javadoc)
     * 
     * @see net.minecraft.block.Block#getDamageValue(net.minecraft.world.World,
     * int, int, int)
     */
    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        return limitToValidMetadata(par1World
                .getBlockMetadata(par2, par3, par4));
    }

    /**
     * returns a number between 0 and ANDLIMIT
     */
    public static int limitToValidMetadata(int par0)
    {
        return par0 & ANDLIMIT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        iconArray = new Icon[BarkHelper.INSTANCE.size()];
        for (int i = 0; i < iconArray.length; ++i)
        {
            iconArray[i] = 
                iconRegister.registerIcon(BlockHelper.INSTANCE.getTexture(i));
        }
    } // end registerIcons()

}
