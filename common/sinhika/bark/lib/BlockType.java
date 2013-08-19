package sinhika.bark.lib;

import com.google.common.base.Optional;

public class BlockType extends ThingType
{
    public String oreDictBarkBlockName; // oreDictionary name of bark BLOCK. 
    public int logID;           // the blockID value for the matching log this bark came from.
    public int metadata;        // the metadata value for the type (0=oak, 1=spruce, etc)
    
    /**
     * 
     * @param blockTexture Optional of basename of texture file for bark BLOCK.
     */
    public BlockType(String typeName, Optional<String> blockTexture, int log_id, int metadata)
    {
        super(typeName);
        this.name = this.typeName + "BarkBlock";
        this.tagLocalized = name + ".name";
        this.oreDictBarkBlockName = "wood" + capTypeName + "BarkBlock";
        this.logID = log_id;
        this.metadata = metadata;
        if (blockTexture.isPresent())
        {
            this.texture = blockTexture.get();
        }
        else {
            this.texture = "log_" + typeName;
        }
    }

    public void setLogID(int logID)
    {
        this.logID = logID;
    }
} // end class
