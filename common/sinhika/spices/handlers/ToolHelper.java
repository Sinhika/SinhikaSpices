/**
 * 
 */
package sinhika.spices.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.common.base.Optional;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import sinhika.spices.lib.ToolType;

/**
 * Encapsulates and provides access to ToolType data.
 * @author Sinhika
 *
 */
public class ToolHelper implements IThingHelper
{
    public static final int DEFAULT_TYPE_SIZE = 5;
    private ArrayList<ToolType> toolTypes;
    public HashMap<EnumToolMaterial, Integer> materialsInUse;
    public static ToolHelper INSTANCE = new ToolHelper();

    public ToolHelper() {
        toolTypes =  new ArrayList<ToolType>(DEFAULT_TYPE_SIZE);
        materialsInUse = new HashMap<EnumToolMaterial,Integer>();
    }
    
/**
 * loads up the vanilla tool materials plus any from this module.
 */
@Override
public void init()
{
    Optional<String> no_string = Optional.absent();
    add(new ToolType("wood", EnumToolMaterial.WOOD, no_string));
    add(new ToolType("stone", EnumToolMaterial.STONE, no_string));
    add(new ToolType("iron", EnumToolMaterial.IRON, no_string));
    add(new ToolType("gold", EnumToolMaterial.GOLD, no_string));
    add(new ToolType("diamond", EnumToolMaterial.EMERALD, no_string));
} // end init


/**
 * check for other tool materials added by other modules, and
     * add tool info for the additional materials. 
 */
public void addSupplementalTools()
{
    Optional<String> no_string = Optional.absent();
    for (EnumToolMaterial tm : EnumToolMaterial.values())
    {
        if (materialsInUse.containsKey(tm)) continue;
        add(new ToolType(tm.toString().toLowerCase(), tm,  
                         no_string));
    } // end-for
} // end addSupplementalTools


public void add(ToolType b) 
{
    if (!materialsInUse.containsKey(b.toolMaterial))
    {
        toolTypes.add(b);
        materialsInUse.put(b.toolMaterial, materialsInUse.size());
    }
} // end add()

public  EnumToolMaterial getToolMaterial(int index)
{
    return toolTypes.get(index).toolMaterial;
}

@Override
public  String getName(int index) {
    return toolTypes.get(index).name;
}

@Override
public  String getCapName(int index) {
    return toolTypes.get(index).capTypeName;
}

@Override
public  String getTypeName(int index) {
    return toolTypes.get(index).typeName;
}

@Override
public  String getTexture(int index) {
    return toolTypes.get(index).texture;
}

@Override
public  int getItemID(int index) {
    return toolTypes.get(index).itemID;
}

@Override
public  void setItemID(int index, int id)
{
    toolTypes.get(index).setItemID(id);
}

@Override
public  String getLocalizationTag(int index)
{
    return toolTypes.get(index).tagLocalized;
}

public  int getHarvestLevel(int index)
{
    return toolTypes.get(index).toolMaterial.getHarvestLevel();
}

public  int getMaxUses(int index)
{
    return toolTypes.get(index).toolMaterial.getMaxUses();
}

public  int getToolCraftingMaterialID(int index)
{
    return toolTypes.get(index).toolMaterial.getToolCraftingMaterial();
}

public  Optional<Item> getToolItem(int index)
{
    return toolTypes.get(index).getToolItem();
}

public  Optional<Block> getToolBlock(int index)
{
    return toolTypes.get(index).getToolBlock();
}

@Override
public  int size() 
{
    return toolTypes.size();
}

} // end class
