package sinhika.spices.lib;

import org.apache.commons.lang3.text.WordUtils;

public class ThingType
{

    public String typeName;
    public String capTypeName;
    public String name;
    public String tagLocalized;
    public int itemID;
    public String texture;

    public ThingType(String typeName)
    {
        super();
        this.typeName = typeName;
        this.capTypeName = WordUtils.capitalizeFully(typeName);
    }

    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }

}
