package sinhika.bark.item;

import sinhika.bark.Bark;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBark extends Item
{

    public ItemBark(int id)
    {
        super(id);
        setMaxStackSize(64);
        setCreativeTab(Bark.customTabSpices);
        setUnlocalizedName("bark");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(func_111208_A());
    } // end registerIcons()
} // class
