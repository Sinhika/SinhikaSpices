package sinhika.bark.lib;

import com.google.common.base.Optional;

/**
 * 
 * @author Sinhika
 *
 */
public class SpiceType extends ThingType
{
    public String oreDictName;  // oreDictionary name of spice item.
    
    /**
     * @param typeName descriptive category
     * @param iconTexture asset path to icon
     * @param oreName oreDictionary name to register.
     */
    public SpiceType(String typeName, Optional<String> iconTexture, String oreName )
    {
        super(typeName);
        this.name = "spice_" + this.typeName;
        this.tagLocalized = name + ".name";
        this.oreDictName = oreName;
        if (iconTexture.isPresent())
        {
            this.texture = iconTexture.get();
        }
        else {
            this.texture = ModInfo.ID + ":" + name;
        }        
    } // end ctor

} // end class
