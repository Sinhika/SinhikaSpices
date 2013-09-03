package sinhika.bark.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

/**
 * special-case crafting hook. In this case, we use it to implement tools that
 * are used in recipes, but not consumed.
 * 
 * @author Sinhika
 * 
 */
public class CraftingHandler implements ICraftingHandler
{

    public CraftingHandler()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * Hook for what happens when crafting done.  Original use for this was incorrect;
     * stub left in case anyone has links to this page.
     * 
     * @param player ignored in this case
     * @param item ignored in this case
     * @param craftMatrix the crafting table object
     */
    @Override
    public void onCrafting(EntityPlayer player, ItemStack item,
            IInventory craftMatrix)
    {
        // Stubbed off until I have a good use for this.
    } // end onCrafting()

    /**
     * The object array contains these two arguments
     * 
     * @param player
     * @param item
     */
    @Override
    public void onSmelting(EntityPlayer player, ItemStack item)
    {
        // TODO Auto-generated method stub

    }

}
