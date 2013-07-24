package sinhika.spices.handlers;

import java.util.ArrayList;

import sinhika.spices.lib.BarkType;
import sinhika.spices.lib.ModInfo;
import net.minecraft.block.Block;

import com.google.common.base.Optional;

/**
 * Encapsulates and provides access to BarkType data.
 * @author Sinhika
 *
 */
public class BarkHelper implements IThingHelper
{
    private static final int DEFAULT_TYPE_SIZE = 4;
    private ArrayList<BarkType> barkTypes;

    public static BarkHelper INSTANCE = new BarkHelper();
    
    public BarkHelper()
    {
        barkTypes = new ArrayList<BarkType>(DEFAULT_TYPE_SIZE);
    }
    /**
     * loads up the vanilla bark types plus any from this module.
     */
    @Override
    public void init()
    {
        Optional<String> no_string = Optional.absent();
        barkTypes.add(new BarkType("oak", no_string, Block.wood.blockID, 0));
        barkTypes.add(new BarkType("spruce", no_string, Block.wood.blockID, 1));
        barkTypes.add(new BarkType("birch", no_string, Block.wood.blockID, 2));
        barkTypes.add(new BarkType("cinnamon", Optional.of(ModInfo.ID + ":cinnamon_sticks"), 
                      Block.wood.blockID, 3));        
    } // end init
    
    public void add(BarkType b) {
        barkTypes.add(b);
    }
    
    /* (non-Javadoc)
     * @see sinhika.spices.handlers.IThingHelper#getName(int)
     */
    @Override
    public String getName(int index) {
        return barkTypes.get(index).name;
    }
    
    public String getOreDictName(int index) {
        return barkTypes.get(index).oreDictBarkName;
    }
    
     
    /* (non-Javadoc)
     * @see sinhika.spices.handlers.IThingHelper#getCapName(int)
     */
    @Override
    public String getCapName(int index) {
        return barkTypes.get(index).capTypeName;
    }
    
    /* (non-Javadoc)
     * @see sinhika.spices.handlers.IThingHelper#getTypeName(int)
     */
    @Override
    public String getTypeName(int index) {
        return barkTypes.get(index).typeName;
    }
    
    @Override
    public String getTexture(int index) {
        return barkTypes.get(index).texture;
    }
    
    public int getMetadata(int index) {
        return barkTypes.get(index).metadata;
    }
    
    /* (non-Javadoc)
     * @see sinhika.spices.handlers.IThingHelper#getItemID(int)
     */
    @Override
    public int getItemID(int index) {
        return barkTypes.get(index).itemID;
    }
    
    /* (non-Javadoc)
     * @see sinhika.spices.handlers.IThingHelper#setItemID(int, int)
     */
    @Override
    public void setItemID(int index, int id)
    {
        barkTypes.get(index).setItemID(id);
    }
    
    public int getLogID(int index) {
       return  barkTypes.get(index).logID;
    }
    
    /* (non-Javadoc)
     * @see sinhika.spices.handlers.IThingHelper#getLocalizationTag(int)
     */
    @Override
    public String getLocalizationTag(int index)
    {
        return barkTypes.get(index).tagLocalized;
    }
    /* (non-Javadoc)
     * @see sinhika.spices.handlers.IThingHelper#size()
     */
    @Override
    public int size() 
    {
        return barkTypes.size();
    }
    
} // end class BarkHelper

