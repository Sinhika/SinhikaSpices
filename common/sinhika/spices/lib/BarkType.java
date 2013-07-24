package sinhika.spices.lib;

import com.google.common.base.Optional;

/**
 * bundle of all the descriptive data on a bark item or block. Used for initializing
 * the bark Item and Block for each type.
 * @author Sinhika
 *
 */
public class BarkType extends ThingType
{
    public String oreDictBarkName;  // oreDictionary name of bark ITEM.
    public String oreDictBarkBlockName; // oreDictionary name of bark BLOCK. 
    public String barkTexture;  // basename of texture file for bark ITEM.
    public String blockTexture; // basename of texture file for bark BLOCK.
    
    public int blockID;         // the blockID value for the bark BLOCK.
    public int logID;           // the blockID value for the matching log this bark came from.

    public int metadata;        // the metadata value for the type (0=oak, 1=spruce, etc)
    
    /**
     * main constructor for BarkType.
     * @param typeName lowercase type name, e.g. 'oak', 'birch', etc.
     * @param oreDictName name for oreDictionary
     * @param barkTexture Optional of basename of texture file for bark ITEM.
     * @param blockTexture Optional of basename of texture file for bark BLOCK.
     * @param logID blockID value for the matching log this bark came from.
     * @param metadata the metadata value for the type (0=oak, 1=spruce, etc)
     */
    public BarkType(String typeName, Optional<String> barkTexture, Optional<String> blockTexture, int log_id, int metadata)
    {
        super(typeName);
        this.oreDictBarkName = "wood" + capTypeName + "Bark";
        this.oreDictBarkBlockName = "wood" + capTypeName + "BarkBlock";
        this.name = this.typeName + "Bark";
        this.tagLocalized = name + ".name";
        
        if (barkTexture.isPresent())
        {
            this.barkTexture = barkTexture.get();
        }
        else {
            this.barkTexture = ModInfo.ID + ":" + typeName + "_bark";
        }
        if (blockTexture.isPresent()) {
            this.blockTexture = blockTexture.get();
        }
        else {
            this.blockTexture = "log_" + typeName;
        }
        this.logID = log_id;
        this.metadata = metadata;
    } // end ctor

    public void setBlockID(int blockID)
    {
        this.blockID = blockID;
    }
    
    public void setLogID(int logID)
    {
        this.logID = logID;
    }
} // end class
