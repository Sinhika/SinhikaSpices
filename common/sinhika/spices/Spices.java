package sinhika.spices;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import sinhika.spices.handlers.ConfigHelper;
import sinhika.spices.handlers.CraftingHandler;
import sinhika.spices.handlers.LocalizationHelper;
import sinhika.spices.handlers.LogHelper;
import sinhika.spices.lib.BarkHelper;
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

    // TODO clean up all this crap

    /** custom creative-mode tab object */
    public static CreativeTabs customTabSpices;

    /** bark blocks */
    public static Block barkBlock;
    private static final String[] barkBlockNames = { "Bundle of Oak Bark",
            "Bundle of Spruce Bark", "Bundle of Birch Bark",
            "Bundle of Cinnamon" };

    /** bark items */
    public static Item[] barkItems;
    private static final String[] barkNames = { "Oak Bark", "Spruce Bark",
            "Birch Bark", "Cinnamon Sticks" };

    /** tools */
    public static Item[] toolItems;
    public static final String[] toolTypes = { "wood", "stone", "iron", "gold",
            "diamond" };
    private static final EnumToolMaterial[] toolMaterials = {
            EnumToolMaterial.WOOD, EnumToolMaterial.STONE,
            EnumToolMaterial.IRON, EnumToolMaterial.GOLD,
            EnumToolMaterial.EMERALD };
    public static int[] harvestLevels = { 0, 1, 2, 0, 3 };
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
        BarkHelper.init();

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
        for (int i = 0; i < BarkHelper.size(); i++)
        {
            int meta = BarkHelper.getMetadata(i);
            ItemStack barkBlockStack = new ItemStack(barkBlock, 1, meta);
            LanguageRegistry.addName(barkBlockStack, barkBlockNames[i]);

            OreDictionary.registerOre(BarkHelper.getOreDictBarkBlockName(i),
                    barkBlockStack);
            LogHelper.info(BarkHelper.getOreDictBarkBlockName(i)
                    + " registered with OreDictionary");
        }
        MinecraftForge.setBlockHarvestLevel(barkBlock, "axe", 0);

        // spice items
        barkItems = new Item[BarkHelper.size()];
        for (int i = 0; i < BarkHelper.size(); i++)
        {
            barkItems[i] = new ItemBark(Configurables.barkItemID[i]);
            barkItems[i].setUnlocalizedName(BarkHelper.getName(i));
            barkItems[i].func_111206_d(BarkHelper.getBarkTexture(i));
            LanguageRegistry.addName(barkItems[i], barkNames[i]);

            OreDictionary.registerOre(BarkHelper.getOreDictBarkName(i),
                    new ItemStack(barkItems[i]));
            LogHelper.info(BarkHelper.getOreDictBarkName(i)
                    + " registered with OreDictionary");
        }
        SpiceTab.init(barkItems[3]);

        // tool items
        toolItems = new Item[toolTypes.length];

        for (int i = 0; i < toolTypes.length; i++)
        {
            toolItems[i] = new ItemSpud(Configurables.toolItemID[i],
                    toolMaterials[i]);
            toolItems[i].setUnlocalizedName(toolTypes[i] + "_spud");
            toolItems[i].func_111206_d(ModInfo.ID + ":" + toolTypes[i]
                    + "_spud");
            LanguageRegistry.addName(toolItems[i], toolTypes[i].substring(0, 1)
                    .toUpperCase() + toolTypes[i].substring(1) + " Bark Spud");
            MinecraftForge.setToolClass(toolItems[i], "spud", harvestLevels[i]);
        } // end for

        for (Block block : ItemSpud.blocksEffectiveAgainst)
        {
            MinecraftForge.setBlockHarvestLevel(block, "spud", 0);
        }
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
        Object[] recipeItems = new Object[] { Block.planks, Block.cobblestone,
                Item.ingotIron, Item.ingotGold, Item.diamond };
        String[] blockPattern = new String[] { "XXX", "XXX", "XXX" };

        // spice test recipes
        // create bark spuds
        for (int i = 0; i < recipeItems.length; ++i)
        {
            GameRegistry.addRecipe(new ItemStack(toolItems[i]), new Object[] {
                    spudPattern, '#', Item.stick, 'X', recipeItems[i] });
        } // end for i

        // peeling bark
        for (int i = 0; i < BarkHelper.size(); i++)
        {
            int meta = BarkHelper.getMetadata(i);
            for (int j = 0; j < toolItems.length; j++)
            {
                GameRegistry.addRecipe(
                        new ShapelessOreRecipe(
                                new ItemStack(barkItems[i], harvestLevels[j] + 3), 
                                new ItemStack(toolItems[j], 1, OreDictionary.WILDCARD_VALUE),
                                new ItemStack(BarkHelper.getLogID(i), 1, meta)));
            } // end-for j
        } // end-for i

        // making & unmaking bark bundles
        for (int i = 0; i < BarkHelper.size(); i++)
        {
            int meta = BarkHelper.getMetadata(i);
            ItemStack barkBlockStack = new ItemStack(barkBlock, 1, meta);
            // OreDict versions
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(barkBlockStack,
                                        new Object[] { blockPattern, 'X',
                                                BarkHelper.getOreDictBarkName(i) }));
            GameRegistry.addRecipe(
                    new ShapelessOreRecipe(
                            new ItemStack(barkItems[i], 9), 
                            BarkHelper.getOreDictBarkBlockName(i)));
        } // end-for
    } // end addRecipes()
} // end class
