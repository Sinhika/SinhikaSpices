package sinhika.spices;

import net.minecraft.creativetab.CreativeTabs;
import sinhika.spices.handlers.BarkHelper;
import sinhika.spices.handlers.BlockHelper;
import sinhika.spices.handlers.ConfigHelper;
import sinhika.spices.handlers.CraftingHandler;
import sinhika.spices.handlers.FuelHandler;
import sinhika.spices.handlers.ItemHelper;
import sinhika.spices.handlers.LocalizationHelper;
import sinhika.spices.handlers.LogHelper;
import sinhika.spices.handlers.RecipeHelper;
import sinhika.spices.handlers.ToolHelper;
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
  
    private static CraftingHandler spiceCraftingHandler;
    private static FuelHandler spiceFuelHandler;
    
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
        BlockHelper.INSTANCE.init();
        
        // initialize configurables
        ConfigHelper.init(event);

        // create handlers
        customTabSpices = new SpiceTab();
        spiceCraftingHandler = new CraftingHandler();
        spiceFuelHandler = new FuelHandler();
    } // end preInit()

    /**
     * Load phase actions go here, such as creating items & blocks, adding
     * recipes, etc.
     */
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        proxy.registerRenderers();

        // spice items
        ItemHelper.init();
        SpiceTab.init(ItemHelper.getBarkItem(3));
     
        // register handlers
        GameRegistry.registerCraftingHandler(spiceCraftingHandler);
        GameRegistry.registerFuelHandler(spiceFuelHandler);
        
        RecipeHelper.addRecipes();
    } // end load()

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Stub Method
    }
} // end class