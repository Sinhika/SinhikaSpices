package sinhika.spices;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemTool;

public class ItemSpud extends ItemTool {
	
	/** an array of the blocks this bark spud is effective against */
    public static final Block[] blocksEffectiveAgainst = 
    		new Block[] {Block.wood, Spices.barkBlock};

	public ItemSpud(int par1, EnumToolMaterial par3EnumToolMaterial) 
	{
		super(par1, 2.0F, par3EnumToolMaterial, blocksEffectiveAgainst);
		setCreativeTab(Spices.customTabSpices);
	}
		
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(this.func_111208_A());
	} // end registerIcons()

} // class
