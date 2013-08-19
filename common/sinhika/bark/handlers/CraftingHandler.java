package sinhika.bark.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
     * Hook for what happens when crafting done. This handler deals with the
     * case of crafting with a tool (bark spud) that is not consumed when used
     * in a recipe.
     * 
     * @param player ignored in this case
     * @param item ignored in this case
     * @param craftMatrix the crafting table object
     */
    @Override
    public void onCrafting(EntityPlayer player, ItemStack item,
            IInventory craftMatrix)
    {
        try
        {
            // cycle through crafting table inventory...
            for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
            {
                // is there even an item in this slot?
                if (craftMatrix.getStackInSlot(i) != null)
                {
                    ItemStack j = craftMatrix.getStackInSlot(i);
                    // is it a spud?
                    if (j.getItem() != null
                            && j.getItemName().endsWith("_spud"))
                    {
                        LogHelper.finest("Item is " + j.getItemName() + "(#"
                                + j.itemID + ")");
                        // if its a spud, create 2: one to get eaten by crafting
                        // table, the
                        // other to be returned to player.
                        ItemStack k = new ItemStack(j.getItem(), 2,
                                j.getItemDamage());

                        // is spud enchanted? If so, must copy enchantments to
                        // new spud(s)
                        if (j.isItemEnchanted())
                        {
                            NBTTagCompound nbtcompound = j.getTagCompound();
                            k.setTagCompound(nbtcompound);
                        }

                        LogHelper.finest("created 2 of item");
                        // actually stick the stack of spuds in the crafting
                        // table slot.
                        craftMatrix.setInventorySlotContents(i, k);
                        LogHelper.finest("Inserted double stack into slot #" + i);
                    } // end-if item is "spud"
                } // end-if j
            } // end-for
        }
        catch (Exception e)
        {
            LogHelper
                    .warning("CraftingHandler::onCrafting: Something went horribly wrong!");
            LogHelper.warning(e.toString());
            e.printStackTrace(System.err);
        }
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
