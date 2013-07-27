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
import sinhika.spices.block.BarkItemBlock;
import sinhika.spices.block.BlockBark;
import sinhika.spices.item.ItemBark;
import sinhika.spices.item.ItemSpud;
import sinhika.spices.lib.Configurables;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Helps set up and initialize Items.
 * 
 * @author Sinhika
 * 
 */
public class ItemHelper
{
    public static ArrayList<Item> barkItems = new ArrayList<Item>(
            BarkHelper.INSTANCE.size());
    public static ArrayList<Item> toolItems = new ArrayList<Item>(
            ToolHelper.INSTANCE.size());
    
    // only 1 block ID used until we start adding non-vanilla woods.
    public static ArrayList<Block> barkBlocks = new ArrayList<Block>(1); 

    /**
     * initialize items.
     */
    public static void init()
    {
        // init bark items
        initBarkItems();
        // init blocks
        initBlocks();
        // init tool items
        initToolItems();
    } // end init()

    
    private static void initBlocks()
    {
        // there is only one blockID until we add non-vanilla blocks.
        Block barkBlock = new BlockBark(Configurables.barkBlockID);
        GameRegistry.registerBlock(barkBlock, BarkItemBlock.class, "barkBlock");
        MinecraftForge.setBlockHarvestLevel(barkBlock, "axe", 0);
       
        for (int i = 0; i < BlockHelper.INSTANCE.size(); i++)
        {
            // save the configuration id to BlockHelper, even if there is
            // only one. It may change in the future.
            BlockHelper.INSTANCE.setItemID(i, Configurables.barkBlockID);

            int meta = BlockHelper.INSTANCE.getMetadata(i);
            ItemStack barkBlockStack = new ItemStack(barkBlock, 1, meta);
            
            // set display name -- should already be set by localization file
            // but set up a makeshift in case it is not.
            String tagLocale = "tile." + BlockHelper.INSTANCE.getLocalizationTag(i);
            if (LocalizationHelper.getLocalizedString(tagLocale).isEmpty())
            {
                LanguageRegistry.addName(barkBlockStack, 
                        "Bundle of " + BlockHelper.INSTANCE.getCapName(i) + " Bark");
            }

            OreDictionary.registerOre(BlockHelper.INSTANCE.getOreDictName(i),
                    barkBlockStack);
            LogHelper.info(BlockHelper.INSTANCE.getOreDictName(i)
                    + " registered with OreDictionary");
        }
        barkBlocks.add(barkBlock);
    } // end initBlocks()
    
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
            barkItem.func_111206_d(BarkHelper.INSTANCE.getTexture(i));

            // set display name -- should already be set by localization file
            // but set up a makeshift in case it is not.
            String tagLocale = "item." + BarkHelper.INSTANCE.getLocalizationTag(i);
            if (LocalizationHelper.getLocalizedString(tagLocale).isEmpty())
            {
                LanguageRegistry.addName(barkItem, BarkHelper.INSTANCE.getCapName(i));
            }

            // add to ore dictionary
            OreDictionary.registerOre(BarkHelper.INSTANCE.getOreDictName(i),
                    new ItemStack(barkItem));
            LogHelper.info(BarkHelper.INSTANCE.getOreDictName(i)
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
            initTool(i, Configurables.toolItemID.get(i));
        } // end-for

        for (Block block : ItemSpud.blocksEffectiveAgainst)
        {
            MinecraftForge.setBlockHarvestLevel(block, "spud", 0);
        }
    } // end initToolItems()

    
    public static void addSupplementalToolItems()
    {
        int toolId;
        
        // do we have extra tool profiles?
        if (toolItems.size() < ToolHelper.INSTANCE.size()) 
        {
            // start at the end of the vanilla list...
            for (int i = toolItems.size(); i < ToolHelper.INSTANCE.size(); i++)
            {
                try {
                    toolId = Configurables.toolItemID.get(i);
                }
                catch (IndexOutOfBoundsException e) {
                    // configure an new id.
                    toolId = ConfigHelper.addTool(ToolHelper.INSTANCE.getToolMaterial(i));
                } // end-catch
                initTool(i, toolId);
            } // end-for
        } // end-if
    } // end addSupplementalToolItems()
    
    /**
     * initialize a tool item.
     * @param toolIndex
     * @param id
     */
    public static void initTool(int toolIndex, int id)
    {
        // save the configuration ID to ToolHelper.
        ToolHelper.INSTANCE.setItemID(toolIndex, id);

        // create & register the new item
        Item toolItem = new ItemSpud(ToolHelper.INSTANCE.getItemID(toolIndex),
                                    ToolHelper.INSTANCE.getToolMaterial(toolIndex));
        
        // set unlocalized name tag
        toolItem.setUnlocalizedName(ToolHelper.INSTANCE.getName(toolIndex));
        // set icon string
        toolItem.func_111206_d(ToolHelper.INSTANCE.getTexture(toolIndex));

        // set display name -- should already be set by localization file
        // but set up a makeshift in case it is not.
        String tagLocale = "item." + ToolHelper.INSTANCE.getLocalizationTag(toolIndex);
        if (LocalizationHelper.getLocalizedString(tagLocale).isEmpty())
        {
            LanguageRegistry.addName(toolItem, ToolHelper.INSTANCE.getCapName(toolIndex)
                    + " Bark Spud");
        }

        MinecraftForge.setToolClass(toolItem, "spud",
                ToolHelper.INSTANCE.getHarvestLevel(toolIndex));
        toolItems.add(toolItem);    
    } // end initTool()
    
    
    public static Item getBarkItem(int index)
    {
        return barkItems.get(index);
    }

    public static Item getToolItem(int index)
    {
        return toolItems.get(index);
    }

    /**
     * gets Block from barkBlocks list.
     * @param index index of block in barkBlocks list.
     * @return Block at index.
     */
    public static Block getBarkBlock(int index)
    {
        return barkBlocks.get(index);
    }
} // end class ItemHelper
