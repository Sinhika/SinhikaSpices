/**
 * 
 */
package sinhika.bark.handlers;

import java.util.ArrayList;

import net.minecraft.block.Block;

import com.google.common.base.Optional;

import sinhika.bark.lib.BlockType;
import sinhika.bark.lib.ModInfo;

/**
 * @author Sinhika
 *
 */
public class BlockHelper implements IThingHelper
{
    private static final int DEFAULT_TYPE_SIZE = 4;
    private ArrayList<BlockType> blockTypes;

    public static BlockHelper INSTANCE = new BlockHelper();
    
    public BlockHelper()
    {
        blockTypes = new ArrayList<BlockType>(DEFAULT_TYPE_SIZE);
    }
    
    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#init()
     */
    @Override
    public void init()
    {
        Optional<String> no_string = Optional.absent();
        blockTypes.add(new BlockType("oak", no_string, Block.wood.blockID, 0));
        blockTypes.add(new BlockType("spruce", no_string, Block.wood.blockID, 1));
        blockTypes.add(new BlockType("birch", no_string, Block.wood.blockID, 2));
        blockTypes.add(new BlockType("cinnamon", 
                Optional.of(ModInfo.ID + ":log_cinnamon"), Block.wood.blockID, 3));
    }

    public void add(BlockType b) {
        blockTypes.add(b);
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getName(int)
     */
    @Override
    public String getName(int index)
    {
        return blockTypes.get(index).name;
    }

    public String getOreDictName(int index) {
        return blockTypes.get(index).oreDictBarkBlockName;
    }
    
    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getCapName(int)
     */
    @Override
    public String getCapName(int index)
    {
        return blockTypes.get(index).capTypeName;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getTypeName(int)
     */
    @Override
    public String getTypeName(int index)
    {
        return blockTypes.get(index).typeName;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getItemID(int)
     */
    @Override
    public int getItemID(int index)
    {
        return blockTypes.get(index).itemID;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#setItemID(int, int)
     */
    @Override
    public void setItemID(int index, int id)
    {
        blockTypes.get(index).setItemID(id);
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getLocalizationTag(int)
     */
    @Override
    public String getLocalizationTag(int index)
    {
        return blockTypes.get(index).tagLocalized;
    }

    public int getLogID(int index) {
        return  blockTypes.get(index).logID;
     }
     
    public int getMetadata(int index) {
        return blockTypes.get(index).metadata;
    }
    
    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getTexture(int)
     */
    @Override
    public String getTexture(int index)
    {
        return blockTypes.get(index).texture;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#size()
     */
    @Override
    public int size()
    {
        return blockTypes.size();
    }

}
