package sinhika.spices;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import sinhika.spices.handlers.BarkHelper;
import sinhika.spices.handlers.ConfigHelper;
import sinhika.spices.handlers.CraftingHandler;
import sinhika.spices.handlers.ItemHelper;
import sinhika.spices.handlers.LocalizationHelper;
import sinhika.spices.handlers.LogHelper;
import sinhika.spices.handlers.ToolHelper;
import sinhika.spices.lib.Configurables;
import sinhika.spices.lib.ModInfo;
import sinhika.spices.network.PacketHandler;
import sinhika.spices.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Module Sinhika's Spices main class.
 * 
 * @author Sinhika (sinhika@republicofnewhome.org)
 * 
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod(channels = { ModInfo.CHANNEL }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class Spices
{
    /** The instance of your mod that Forge uses. */
    @Instance(ModInfo.ID)
    public static Spices instance;

    /** custom creative-mode tab object */
    public static CreativeTabs customTabSpices;

    // TODO clean up all this crap
    /** bark blocks */
    public static Block barkBlock;
    private static final String[] barkBlockNames = { "Bundle of Oak Bark",
            "Bundle of Spruce Bark", "Bundle of Birch Bark",
            "Bundle of Cinnamon" };
  
    /** tools */
    private static CraftingHandler spiceCraftingHandler;

    /** Says where the client and server 'proxy' code is loaded. */
    @SidedProxy(clientSide = "sinhika.spices.proxy.ClientProxy", serverSide = "sinhika.spices.proxy.CommonProxy")
    public static CommonProxy proxy;

    /**
     * preInit phase actions go here, such as reading config files and setting
     * up logger.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // initialize logging
        LogHelper.init();

        // get language localizations
        LocalizationHelper.loadLanguages();
        
        // load basic item info
        BarkHelper.INSTANCE.init();
        ToolHelper.INSTANCE.init();
        
        // initialize configurables
        ConfigHelper.init(event);

        // create handlers
        customTabSpices = new SpiceTab();
        spiceCraftingHandler = new CraftingHandler();
    } // end preInit()

    /**
     * Load phase actions go here, such as creating items & blocks, adding
     * recipes, etc.
     */
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        proxy.registerRenderers();

        // spice blocks
        barkBlock = new BlockBark(Configurables.barkBlockID);
        GameRegistry.registerBlock(barkBlock, BarkItemBlock.class, "barkBlock");
        for (int i = 0; i < BarkHelper.INSTANCE.size(); i++)
        {
            int meta = BarkHelper.INSTANCE.getMetadata(i);
            ItemStack barkBlockStack = new ItemStack(barkBlock, 1, meta);
            LanguageRegistry.addName(barkBlockStack, barkBlockNames[i]);

            OreDictionary.registerOre(BarkHelper.INSTANCE.getOreDictBarkBlockName(i),
                    barkBlockStack);
            LogHelper.info(BarkHelper.INSTANCE.getOreDictBarkBlockName(i)
                    + " registered with OreDictionary");
        }
        MinecraftForge.setBlockHarvestLevel(barkBlock, "axe", 0);

        // spice items
        ItemHelper.init();
        SpiceTab.init(ItemHelper.getBarkItem(3));
     
        GameRegistry.registerCraftingHandler(spiceCraftingHandler);
        addRecipes();
    } // end load()

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Stub Method
    }

    /**
     * add module recipes; moved to separate method to reduce clutter in load().
     * 
     */
    protected void addRecipes()
    {
        String[] spudPattern = new String[] { " #X", " # ", " # " };
        String[] blockPattern = new String[] { "XXX", "XXX", "XXX" };

        // spice test recipes
        // create bark spuds
        for (int i = 0; i < ToolHelper.INSTANCE.size(); ++i)
        {
            Object recipeItem;
            if (ToolHelper.INSTANCE.getToolItem(i).isPresent())
            {
                recipeItem = ToolHelper.INSTANCE.getToolItem(i).get();
            }
            else {
                recipeItem = ToolHelper.INSTANCE.getToolBlock(i).get();
            }
            GameRegistry.addRecipe( 
                    new ShapedOreRecipe( new ItemStack(ItemHelper.getToolItem(i)),
                                          new Object[] { spudPattern, 
                                                         '#', "stickWood", 
                                                         'X', recipeItem }));
        } // end for i

        // peeling bark
        for (int i = 0; i < BarkHelper.INSTANCE.size(); i++)
        {
            int meta = BarkHelper.INSTANCE.getMetadata(i);
            for (int j = 0; j < ToolHelper.INSTANCE.size(); j++)
            {
                GameRegistry.addRecipe(
                        new ShapelessOreRecipe(
                                new ItemStack(ItemHelper.getBarkItem(i), 
                                              ToolHelper.INSTANCE.getHarvestLevel(j) + 3), 
                                new ItemStack(ItemHelper.getToolItem(j), 
                                               1, 
                                               OreDictionary.WILDCARD_VALUE),
                                new ItemStack(BarkHelper.INSTANCE.getLogID(i), 1, meta)));
            } // end-for j
        } // end-for i

        // making & unmaking bark bundles
        for (int i = 0; i < BarkHelper.INSTANCE.size(); i++)
        {
            int meta = BarkHelper.INSTANCE.getMetadata(i);
            ItemStack barkBlockStack = new ItemStack(barkBlock, 1, meta);
            // OreDict versions
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(barkBlockStack,
                                        new Object[] { blockPattern, 'X',
                                                BarkHelper.INSTANCE.getOreDictBarkName(i) }));
            GameRegistry.addRecipe(
                    new ShapelessOreRecipe(
                            new ItemStack(ItemHelper.getBarkItem(i), 9), 
                            BarkHelper.INSTANCE.getOreDictBarkBlockName(i)));
        } // end-for
    } // end addRecipes()
} // end class
