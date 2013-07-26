package sinhika.spices.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * package up recipe functions in one place.
 * @author Sinhika
 *
 */
public class RecipeHelper
{
    private static String[] spudPattern = new String[] { " #X", " # ", " # " };
    private static String[] blockPattern = new String[] { "XXX", "XXX", "XXX" };
    
    /**
     * add module recipes; moved to separate method to reduce clutter in load().
     * 
     */
    public static void addRecipes()
    {
        // create bark spuds
        for (int i = 0; i < ToolHelper.INSTANCE.size(); ++i)
        {
           GameRegistry.addRecipe( makeNewSpudRecipe(i));
        } // end for i

        // peeling bark
        for (int i = 0; i < BarkHelper.INSTANCE.size(); i++)
        {
            for (int j = 0; j < ToolHelper.INSTANCE.size(); j++)
            {
                GameRegistry.addRecipe(makeNewPeeledBarkRecipe(i, j));
            } // end-for j
        } // end-for i

        // making & unmaking bark bundles
        for (int i = 0; i < BlockHelper.INSTANCE.size(); i++)
        {
            GameRegistry.addRecipe(makeNewBarkBlockRecipe(i, 0));
            GameRegistry.addRecipe(makeNewSplitBarkRecipe(i));                   
        } // end-for
    } // end addRecipes()
    
    /**
     * create recipe for bark split from bundles.
     * @param blockIndex
     * @return
     */
    public static ShapelessOreRecipe makeNewSplitBarkRecipe(int blockIndex)
    {
        return new ShapelessOreRecipe(
                new ItemStack(ItemHelper.getBarkItem(blockIndex), 9),
                BlockHelper.INSTANCE.getOreDictName(blockIndex));
    } // end makeNewSplitBarkRecipe()
    
    
    /**
     * create recipe for bark bundles.
     * @param blockIndex
     * @param barkBlockIndex
     * @return
     */
    public static ShapedOreRecipe makeNewBarkBlockRecipe(int blockIndex, 
                                                         int barkBlockIndex)
    {
        int meta = BlockHelper.INSTANCE.getMetadata(blockIndex);
        ItemStack barkBlockStack = 
                new ItemStack(ItemHelper.getBarkBlock(barkBlockIndex), 1, meta);
        return new ShapedOreRecipe(barkBlockStack,
                            new Object[] { blockPattern, 'X',
                            BarkHelper.INSTANCE.getOreDictName(blockIndex) });
    }
                                                         
    /**
     * Creates a peeled bark recipe.
     * @param barkIndex index into BarkHelper list
     * @param toolIndex index into ToolHelper list
     * @param meta metadata value
     * @return ShapelessOreRecipe that creates a stack of bark.
     */
    public static ShapelessOreRecipe makeNewPeeledBarkRecipe(int barkIndex, 
                                                             int toolIndex)
    {
        int meta = BarkHelper.INSTANCE.getMetadata(barkIndex);
        return new ShapelessOreRecipe(
                        new ItemStack(ItemHelper.getBarkItem(barkIndex), 
                                      ToolHelper.INSTANCE.getHarvestLevel(toolIndex) + 3), 
                        new ItemStack(ItemHelper.getToolItem(toolIndex), 
                                       1, 
                                       OreDictionary.WILDCARD_VALUE),
                        new ItemStack(BarkHelper.INSTANCE.getLogID(barkIndex), 1, meta));        
    } // makeNewPeeledBarkRecipe
    
    
    /**
     * given an index into the ToolHelper list, create the corresponding bark spud recipe.
     * @param toolIndex index of spud in the ToolHelper list.
     * @return newly-created ShapedOreRecipe for a bark spud.
     */
    public static ShapedOreRecipe makeNewSpudRecipe(int toolIndex)
    {
        Object recipeItem;
        if (ToolHelper.INSTANCE.getToolItem(toolIndex).isPresent())
        {
            recipeItem = ToolHelper.INSTANCE.getToolItem(toolIndex).get();
        }
        else if (ToolHelper.INSTANCE.getToolBlock(toolIndex).isPresent()) 
        {
            recipeItem = ToolHelper.INSTANCE.getToolBlock(toolIndex).get();
        }
        else {
            return null;
        }
        return new ShapedOreRecipe( 
                new ItemStack(ItemHelper.getToolItem(toolIndex)),
                                new Object[] { spudPattern, 
                                               '#', "stickWood", 
                                               'X', recipeItem });  
    } // end makeNewSpudRecipe()
    
} // end class
