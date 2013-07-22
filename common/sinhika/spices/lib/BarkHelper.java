package sinhika.spices.lib;

import java.util.ArrayList;

import net.minecraft.block.Block;

import com.google.common.base.Optional;

public class BarkHelper
{
    private static final int DEFAULT_TYPE_SIZE = 4;
    private static ArrayList<BarkType> barkTypes = new ArrayList<BarkType>(DEFAULT_TYPE_SIZE);

    /**
     * loads up the vanilla bark types plus any from this module.
     */
    public static void init()
    {
        Optional<String> no_string = Optional.absent();
        barkTypes.add(new BarkType("oak", no_string, no_string, Block.wood.blockID, 0));
        barkTypes.add(new BarkType("spruce", no_string, no_string, Block.wood.blockID, 1));
        barkTypes.add(new BarkType("birch", no_string, no_string, Block.wood.blockID, 2));
        barkTypes.add(new BarkType("cinnamon", Optional.of(ModInfo.ID + ":cinnamon_sticks"), 
                    Optional.of(ModInfo.ID + ":log_cinnamon"), Block.wood.blockID, 3));
        
    } // end init
    
    public static void add(BarkType b) {
        barkTypes.add(b);
    }
    
    public static String getName(int index) {
        return barkTypes.get(index).name;
    }
    
    public static String getOreDictBarkName(int index) {
        return barkTypes.get(index).oreDictBarkName;
    }
    
    public static String getOreDictBarkBlockName(int index) {
        return barkTypes.get(index).oreDictBarkBlockName;
    }
    
    public static String getCapName(int index) {
        return barkTypes.get(index).capTypeName;
    }
    
    public static String getTypeName(int index) {
        return barkTypes.get(index).typeName;
    }
    
    public static String getBlockTexture(int index) {
        return barkTypes.get(index).blockTexture;
    }
    
    public static String getBarkTexture(int index) {
        return barkTypes.get(index).barkTexture;
    }
    
    public static int getMetadata(int index) {
        return barkTypes.get(index).metadata;
    }
    
    public static int getItemID(int index) {
        return barkTypes.get(index).itemID;
    }
    
    public static void setItemID(int index, int id)
    {
        barkTypes.get(index).setItemID(id);
    }
    
    public static int getBlockID(int index) {
        return barkTypes.get(index).blockID;
    }
    
    public static void setBlockID(int index, int id) {
        barkTypes.get(index).setBlockID(id);
    }
    
    public static int getLogID(int index) {
       return  barkTypes.get(index).logID;
    }
    
    public static int size() 
    {
        return barkTypes.size();
    }
    
} // end class BarkHelper

