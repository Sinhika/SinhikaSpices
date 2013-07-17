package sinhika.spices;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.ICraftingHandler;
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
 * @author Sinhika (sinhika@republicofnewhome.org)
 *
 */
@Mod(modid=ModInfo.ID, name=ModInfo.NAME, version=ModInfo.VERSION)
@NetworkMod(clientSideRequired=true, serverSideRequired=true)
public class Spices {
    /** The instance of your mod that Forge uses. */
    @Instance(ModInfo.ID)
    public static Spices instance;
    
    /** logger object */
    public static Logger log;
    
    /** custom creative-mode tab object */
    public static CreativeTabs customTabSpices;
    
    /** bark wood types used in this module */
    public final static String[] barkTypes = {"oak", "spruce", "birch", "cinnamon"};
    private final static String[] capBarkTypes = {"Oak", "Spruce", "Birch", "Cinnamon"};
    
    /** bark blocks */
    public static Block barkBlock;
    public static String[] oreDictBarkBlockNames;
    private static final String[] barkBlockNames = {"Bundle of Oak Bark", "Bundle of Spruce Bark", "Bundle of Birch Bark", "Bundle of Cinnamon" };
   	
    /** bark items  */
    public static Item[] barkItems;
    public static String[] oreDictBarkNames;
    private static final String[] barkNames = {"Oak Bark", "Spruce Bark", "Birch Bark", "Cinnamon Sticks" };

     /** tools */
    public static Item [] toolItems;
    private static final String[] toolTypes = {"wood", "stone", "iron", "gold", "diamond"};  
    private static final EnumToolMaterial[] toolMaterials = 
    	{EnumToolMaterial.WOOD, EnumToolMaterial.STONE, EnumToolMaterial.IRON, EnumToolMaterial.GOLD, EnumToolMaterial.EMERALD};
    public static int [] harvestLevels = { 0, 1, 2, 0, 3};
    private static CraftingHandler spiceCraftingHandler;
    
    /** Says where the client and server 'proxy' code is loaded. */
    @SidedProxy(clientSide="sinhika.spices.client.ClientProxy", 
    			serverSide="sinhika.spices.CommonProxy")
    public static CommonProxy proxy;

   
    /** preInit phase actions go here, such as reading config files and setting up logger. */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	// get a logger
    	log = event.getModLog();
    	// get the configurator
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	// load configuration from file
    	config.load();
    	
    	// get logging level
		Property logProp = config.get(config.CATEGORY_GENERAL, "logLevel", "ALL");
		logProp.comment = "logging level ('ALL', 'OFF', 'FINEST' to 'SEVERE'; per Java Logger API)";
    	try {
    		ModInfo.loggingLevel = Level.parse(logProp.getString());
    	}
    	catch (IllegalArgumentException e) {
    		ModInfo.loggingLevel = Level.ALL;
    		log.warning("Bad logLevel in config file; changed to 'ALL'");
    		logProp.set(ModInfo.loggingLevel.toString());
    	}
    	log.setLevel(ModInfo.loggingLevel);
    	log.info("Config file " + event.getSuggestedConfigurationFile() + " read.");
    	log.info("Logging level set to " + ModInfo.loggingLevel.toString());
    	
    	// get block IDs
    	ModInfo.barkBlockID = 
    			config.getBlock("barkBlockID", ModInfo.DEFAULT_BLOCKID, "block ID for bark").getInt();
    	log.info("barkBlockID=" + ModInfo.barkBlockID);
    	
    	// get item IDs
    	ModInfo.barkItemID  = new int [barkTypes.length];
    	for (int i= 0; i < barkTypes.length; i++) {
    		String cfgKey = barkTypes[i] + "BarkItemID";
    		ModInfo.barkItemID[i] = 
    				config.getItem(cfgKey, ModInfo.DEFAULT_BASE_ITEMID + i).getInt();
    		log.info("barkItemID[" + i + "]=" + cfgKey + "=" + ModInfo.barkItemID[i]);
    	}
    	
    	// get tool item IDs
    	ModInfo.toolItemID = new int [toolTypes.length];
    	for (int i=0; i < toolTypes.length; i++) {
    		String cfgKey = toolTypes[i] + "SpudItemID";
    		ModInfo.toolItemID[i] =
    				config.getItem("tools", cfgKey, 
    							  ModInfo.DEFAULT_BASE_ITEMID + barkTypes.length + i).getInt();
    		log.info("toolItem[" + i + "]=" + cfgKey + "=" + ModInfo.toolItemID[i]);
    	}
    	
    	// save configuration back to file
    	config.save();
    	
    	// create handlers
    	customTabSpices = new SpiceTab();
    	spiceCraftingHandler = new CraftingHandler();
    } // end preInit()
   
    /** Load phase actions go here, such as creating items & blocks, adding recipes, etc. */
    @EventHandler
    public void load(FMLInitializationEvent event) {
            proxy.registerRenderers();
                        
            // spice blocks
            barkBlock = new BlockBark(ModInfo.barkBlockID);
            GameRegistry.registerBlock(barkBlock, BarkItemBlock.class, "barkBlock");
            oreDictBarkBlockNames = new String[barkTypes.length];
            for (int i = 0; i < barkTypes.length; i++) {
            	ItemStack barkBlockStack = new ItemStack(barkBlock, 1, i);
            	LanguageRegistry.addName(barkBlockStack, barkBlockNames[i]);
            	oreDictBarkBlockNames[i] = "wood" + capBarkTypes[i] + "BarkBlock";
            	OreDictionary.registerOre(oreDictBarkBlockNames[i], barkBlockStack);
            	log.info(oreDictBarkBlockNames[i] + " registered with OreDictionary");
            }                     
            MinecraftForge.setBlockHarvestLevel(barkBlock, "axe", 0);
            
            // spice items
            barkItems = new Item [barkTypes.length];
        	oreDictBarkNames = new String[barkTypes.length];
            for (int i=0; i < barkTypes.length; i++) {
            	barkItems[i] = new ItemBark(ModInfo.barkItemID[i]);
            	barkItems[i].setUnlocalizedName(barkTypes[i] + "Bark");
            	if (i != 3) {
            		barkItems[i].func_111206_d(ModInfo.ID + ":" + barkTypes[i] + "_bark");
            	}
            	else {   // cinnamon is different
            		barkItems[i].func_111206_d(ModInfo.ID + ":cinnamon_sticks");
            	}
            	LanguageRegistry.addName(barkItems[i], barkNames[i]);
            	oreDictBarkNames[i] = "wood" + capBarkTypes[i] + "Bark";
            	OreDictionary.registerOre(oreDictBarkNames[i], new ItemStack(barkItems[i]));
            	log.info(oreDictBarkNames[i] + " registered with OreDictionary");
            }
        	SpiceTab.init(barkItems[3]);
            
            // tool items
            toolItems = new Item [toolTypes.length];
            
            for (int i=0; i < toolTypes.length; i++) {
            	toolItems[i] = new ItemSpud(ModInfo.toolItemID[i], toolMaterials[i]);
            	toolItems[i].setUnlocalizedName(toolTypes[i] + "_spud");
            	toolItems[i].func_111206_d(ModInfo.ID + ":" + toolTypes[i] + "_spud");
            	LanguageRegistry.addName(toolItems[i], 
            						     toolTypes[i].substring(0,1).toUpperCase() + toolTypes[i].substring(1) + " Bark Spud");
            	MinecraftForge.setToolClass(toolItems[i], "spud", harvestLevels[i]);
            } // end for
 
            for (Block block : ItemSpud.blocksEffectiveAgainst)
            {
                MinecraftForge.setBlockHarvestLevel(block, "spud", 0);
            }
            GameRegistry.registerCraftingHandler(spiceCraftingHandler);                  
            this.addRecipes();
     } // end load()
   
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    }
    
    /** add module recipes; moved to separate method to reduce clutter in load().
     * 
     */
    protected void addRecipes()  {
    	String[] spudPattern = new String [] {" #X"," # ", " # " };
    	Object[] recipeItems = new  Object[] {Block.planks, Block.cobblestone, Item.ingotIron, Item.ingotGold, Item.diamond};
        String[] blockPattern = new String [] {"XXX", "XXX", "XXX"};
        
        // spice test recipes
        // create bark spuds
        for (int i = 0; i < recipeItems.length; ++i)
        {
            GameRegistry.addRecipe(new ItemStack(toolItems[i]), new Object[] {spudPattern, '#', Item.stick, 'X', recipeItems[i]});
        } // end for i
        
        // peeling bark
        for (int i = 0; i < barkItems.length; i++) {
        	for (int j=0; j < toolItems.length; j++) {
        		GameRegistry.addRecipe(
        				new ShapelessOreRecipe(new ItemStack(barkItems[i], harvestLevels[j]+3), 
        				new ItemStack(toolItems[j], 1, OreDictionary.WILDCARD_VALUE), 
        				new ItemStack(Block.wood, 1, i)));
        	} // end-for j
        } // end-for i
        
        // making & unmaking bark bundles
        for (int i=0; i < barkItems.length; i++) {
        	ItemStack barkBlockStack = new ItemStack(barkBlock, 1, i);
        	// OreDict versions
        	GameRegistry.addRecipe(new ShapedOreRecipe(barkBlockStack, 
					        	   new Object[] {blockPattern, 'X', oreDictBarkNames[i]}));
        	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(barkItems[i], 9), 
        						   oreDictBarkBlockNames[i]));
        	
        	// in-game item/block versions
        	GameRegistry.addRecipe(new ShapedOreRecipe(barkBlockStack, 
		        	   new Object[] {blockPattern, 'X', barkItems[i]}));
        	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(barkItems[i], 9), 
        			barkBlockStack));
        } // end-for
    } // end addRecipes()
} // end class
