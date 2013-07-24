package sinhika.spices.lib;

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
        this.capTypeName = typeName.substring(0,1).toUpperCase() + typeName.substring(1);
    }

    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }

}
