/**
 * 
 */
package sinhika.spices.handlers;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import sinhika.spices.ItemBark;
import sinhika.spices.ItemSpud;
import sinhika.spices.lib.Configurables;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Helps set up and initialize Items.
 * 
 * @author Sinhika
 * 
 */
public class ItemHelper
{
    private static final int DEFAULT_BARK_SIZE = 4;

    public static ArrayList<Item> barkItems = new ArrayList<Item>(
            DEFAULT_BARK_SIZE);
    public static ArrayList<Item> toolItems = new ArrayList<Item>(
            ToolHelper.INSTANCE.size());

    /**
     * initialize items.
     */
    public static void init()
    {
        // init bark items
        initBarkItems();
        // init tool items
        initToolItems();
    }

    /**
     * initialize peeled bark items.
     */
    private static void initBarkItems()
    {
        for (int i = 0; i < BarkHelper.INSTANCE.size(); i++)
        {
            // save the configuration id to BarkHelper
            BarkHelper.INSTANCE.setItemID(i, Configurables.barkItemID[i]);

            // create and register the new item.
            Item barkItem = new ItemBark(BarkHelper.INSTANCE.getItemID(i));

            // set unlocalized name tag
            barkItem.setUnlocalizedName(BarkHelper.INSTANCE.getName(i));
            // set icon string
            barkItem.func_111206_d(BarkHelper.INSTANCE.getBarkTexture(i));

            // set display name -- should already be set by localization file
            // but set up a makeshift in case it is not.
            String tagLocale = "item." + BarkHelper.INSTANCE.getLocalizationTag(i);
            if (LocalizationHelper.getLocalizedString(tagLocale).isEmpty())
            {
                LanguageRegistry.addName(barkItem, BarkHelper.INSTANCE.getCapName(i));
            }

            // add to ore dictionary
            OreDictionary.registerOre(BarkHelper.INSTANCE.getOreDictBarkName(i),
                    new ItemStack(barkItem));
            LogHelper.info(BarkHelper.INSTANCE.getOreDictBarkName(i)
                    + " registered with OreDictionary");
            // add to list
            barkItems.add(barkItem);
        } // end for
    } // end initBarkItems()

    /**
     * initialize tool items.
     */
    private static void initToolItems()
    {
        for (int i = 0; i < ToolHelper.INSTANCE.size(); i++)
        {
            // save the configuration ID to ToolHelper.
            ToolHelper.INSTANCE.setItemID(i, Configurables.toolItemID[i]);

            // create & register the new item
            Item toolItem = new ItemSpud(ToolHelper.INSTANCE.getItemID(i),
                    ToolHelper.INSTANCE.getToolMaterial(i));
            // set unlocalized name tag
            toolItem.setUnlocalizedName(ToolHelper.INSTANCE.getName(i));
            // set icon string
            toolItem.func_111206_d(ToolHelper.INSTANCE.getToolTexture(i));

            // set display name -- should already be set by localization file
            // but set up a makeshift in case it is not.
            String tagLocale = "item." + ToolHelper.INSTANCE.getLocalizationTag(i);
            if (LocalizationHelper.getLocalizedString(tagLocale).isEmpty())
            {
                LanguageRegistry.addName(toolItem, ToolHelper.INSTANCE.getCapName(i)
                        + "Bark Spud");
            }

            MinecraftForge.setToolClass(toolItem, "spud",
                    ToolHelper.INSTANCE.getHarvestLevel(i));
            toolItems.add(toolItem);
        } // end-for

        for (Block block : ItemSpud.blocksEffectiveAgainst)
        {
            MinecraftForge.setBlockHarvestLevel(block, "spud", 0);
        }
    } // end initToolItems

    public static Item getBarkItem(int index)
    {
        return barkItems.get(index);
    }

    public static Item getToolItem(int index)
    {
        return toolItems.get(index);
    }

} // end class ItemHelper
