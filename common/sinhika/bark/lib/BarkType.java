package sinhika.bark.lib;

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
    public int logID;           // the blockID value for the matching log this bark came from.
    public int metadata;        // the metadata value for the type (0=oak, 1=spruce, etc)
    
    /**
     * main constructor for BarkType.
     * @param typeName lowercase type name, e.g. 'oak', 'birch', etc.
     * @param oreDictName name for oreDictionary
     * @param barkTexture Optional of basename of texture file for bark ITEM.
     
     * @param logID blockID value for the matching log this bark came from.
     * @param metadata the metadata value for the type (0=oak, 1=spruce, etc)
     */
    public BarkType(String typeName, Optional<String> barkTexture, int log_id, int metadata)
    {
        super(typeName);
        this.oreDictBarkName = "wood" + capTypeName + "Bark";

        this.name = this.typeName + "Bark";
        this.tagLocalized = name + ".name";
        
        if (barkTexture.isPresent())
        {
            this.texture = barkTexture.get();
        }
        else {
            this.texture = ModInfo.ID + ":" + typeName + "_bark";
        }
        this.logID = log_id;
        this.metadata = metadata;
    } // end ctor

  
    public void setLogID(int logID)
    {
        this.logID = logID;
    }
} // end class
