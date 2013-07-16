package sinhika.spices;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBark extends Item {

	public ItemBark(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(Spices.customTabSpices);
		setUnlocalizedName("bark");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(this.func_111208_A());
	} // end registerIcons()
} // class
