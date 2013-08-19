package sinhika.bark.handlers;

import java.util.ArrayList;

import com.google.common.base.Optional;

import sinhika.bark.lib.ModInfo;
import sinhika.bark.lib.SpiceType;

/**
 * @author Sinhika
 *
 */
public class SpiceHelper implements IThingHelper
{
    private static final int DEFAULT_SPICE_COUNT = 1;
    private ArrayList<SpiceType> spiceTypes;
    
    public static SpiceHelper INSTANCE = new SpiceHelper();
    
    /**
     * 
     */
    public SpiceHelper()
    {
        spiceTypes = new ArrayList<SpiceType>(DEFAULT_SPICE_COUNT);
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#init()
     */
    @Override
    public void init()
    {
        String icon = new String(ModInfo.ID + ":powder_cinnamon");
        spiceTypes.add(
                new SpiceType("cinnamon", Optional.of(icon),
                        "foodGroundcinnamon"));
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getName(int)
     */
    @Override
    public String getName(int index)
    {
        return spiceTypes.get(index).name;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getCapName(int)
     */
    @Override
    public String getCapName(int index)
    {
        return spiceTypes.get(index).capTypeName;
    }
    
    public String getOreDictName(int index) {
        return spiceTypes.get(index).oreDictName;
    }
    
    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getTypeName(int)
     */
    @Override
    public String getTypeName(int index)
    {
        return spiceTypes.get(index).typeName;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getItemID(int)
     */
    @Override
    public int getItemID(int index)
    {
        return spiceTypes.get(index).itemID;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#setItemID(int, int)
     */
    @Override
    public void setItemID(int index, int id)
    {
        spiceTypes.get(index).setItemID(id);
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getLocalizationTag(int)
     */
    @Override
    public String getLocalizationTag(int index)
    {
        return spiceTypes.get(index).tagLocalized;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#getTexture(int)
     */
    @Override
    public String getTexture(int index)
    {
        return spiceTypes.get(index).texture;
    }

    /* (non-Javadoc)
     * @see sinhika.bark.handlers.IThingHelper#size()
     */
    @Override
    public int size()
    {
        return spiceTypes.size();
    }

} // end class
