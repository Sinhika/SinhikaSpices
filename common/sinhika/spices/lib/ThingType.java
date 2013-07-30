package sinhika.spices.lib;

import org.apache.commons.lang3.text.WordUtils;

/**
 * abstract superclass for bundling up descriptive data on mod items.
 * Subclass for specific types of items.
 * @author Sinhika
 * @see BarkType, BlockType, ToolType
 *
 */
public abstract class ThingType
{

    public String typeName;     // what type/color/material is it?
    public String capTypeName;  // capitalized typeName
    public String name;         // unlocalized name, or stem of same.
    public String tagLocalized; // tag for looking up localized name in localizations.
    public int itemID;          // the itemID
    public String texture;      // standard texture path to item icon.

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
