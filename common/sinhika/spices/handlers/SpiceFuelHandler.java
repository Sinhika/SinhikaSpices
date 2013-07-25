package sinhika.spices.handlers;

import sinhika.spices.ItemBark;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class SpiceFuelHandler implements IFuelHandler
{

    @Override
    public int getBurnTime(ItemStack fuel)
    {
        if (fuel.getItem() instanceof ItemBark)
        {
            return 100;
        }
        return 0;
    }
}
