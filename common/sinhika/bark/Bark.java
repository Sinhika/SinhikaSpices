package sinhika.bark;

import net.minecraft.creativetab.CreativeTabs;
import sinhika.bark.entity.EntityCanoe;
import sinhika.bark.handlers.BarkHelper;
import sinhika.bark.handlers.BlockHelper;
import sinhika.bark.handlers.ConfigHelper;
import sinhika.bark.handlers.FuelHandler;
import sinhika.bark.handlers.ItemHelper;
import sinhika.bark.handlers.LocalizationHelper;
import sinhika.bark.handlers.LogHelper;
import sinhika.bark.handlers.RecipeHelper;
import sinhika.bark.handlers.SpiceHelper;
import sinhika.bark.handlers.ToolHelper;
import sinhika.bark.lib.Configurables;
import sinhika.bark.lib.ModInfo;
import sinhika.bark.network.PacketHandler;
import sinhika.bark.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Module Sinhika's Bark main class.
 * 
 * @author Sinhika (sinhika@republicofnewhome.org)
 * 
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod(channels = { ModInfo.CHANNEL }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class Bark
{
    /** The instance of your mod that Forge uses. */
    @Instance(ModInfo.ID)
    public static Bark instance;

    /** custom creative-mode tab object */
    public static CreativeTabs customTabSpices;
  
 //   private static CraftingHandler spiceCraftingHandler;
    private static FuelHandler spiceFuelHandler;
    
    /** Says where the client and server 'proxy' code is loaded. */
    @SidedProxy(clientSide = "sinhika.bark.proxy.ClientProxy", serverSide = "sinhika.bark.proxy.CommonProxy")
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
        SpiceHelper.INSTANCE.init();
        
        // initialize configurables (also inits logLevel)
        ConfigHelper.init(event);

        // create handlers
        customTabSpices = new SpiceTab();
        spiceFuelHandler = new FuelHandler();
        
        // register entities
        EntityRegistry.registerModEntity(EntityCanoe.class, "Birch Bark Canoe", 
                           1, Bark.instance, 80, 3, true);
    } // end preInit()

    /**
     * Load phase actions go here, such as creating items & blocks, adding
     * recipes, etc.
     */
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        // reset logLevel in case Forge changed it.
        LogHelper.setLevel(Configurables.loggingLevel);
        proxy.registerRenderers();

        // module items
        ItemHelper.init();
        SpiceTab.init(ItemHelper.getBarkItem(3));
     
        // register handlers
        GameRegistry.registerFuelHandler(spiceFuelHandler);
        
        RecipeHelper.addRecipes();
    } // end load()

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // reset logLevel in case Forge changed it.
        LogHelper.setLevel(Configurables.loggingLevel);
        
        // Add extras
        ModInfo.findMods();
        ToolHelper.INSTANCE.addSupplementalTools();
        ItemHelper.addSupplementalToolItems();
        RecipeHelper.AddSupplementalToolRecipes();
    }
} // end class