package sinhika.spices;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockBark extends Block {
	/** The type of tree this block came from. */
    private static final String[] iconString = new String [] {"log_oak", "log_spruce", "log_birch", ModInfo.ID + ":log_cinnamon"};
    private static int ANDLIMIT = 3; // change if number of Spices.barkTypes changes.
    
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
    
	public BlockBark(int par1) {
		super(par1, Material.wood);
		this.setCreativeTab(Spices.customTabSpices);
		this.setStepSound(Block.soundWoodFootstep);
		this.setHardness(1.5F);
        this.setUnlocalizedName("barkBlock");
        this.func_111022_d("log");
    }

	@Override
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        if (par2 < 0 || par2 >= this.iconArray.length)
        {
            par2 = 0;
        }
        return this.iconArray[par2];
    }
 
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
	@Override
    public int damageDropped(int par1)
    {
        return par1;
    }

	/**
     * returns a list of blocks with the same ID, but different meta (eg: bark returns 4 blocks)
     */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List subItems)
    {
		for (int i=0; i < Spices.barkTypes.length; i++) {
			subItems.add(new ItemStack(par1, 1, i));
		}
    } // end getSubBlocks()
    
	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#getDamageValue(net.minecraft.world.World, int, int, int)
	 */
	@Override
	public int getDamageValue(World par1World, int par2, int par3, int par4) {
		return this.limitToValidMetadata(par1World.getBlockMetadata(par2, par3, par4));
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
	public void registerIcons(IconRegister iconRegister) {
        this.iconArray = new Icon[Spices.barkTypes.length];
        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = iconRegister.registerIcon(iconString[i]);
        }
    } // end registerIcons()
	
}
