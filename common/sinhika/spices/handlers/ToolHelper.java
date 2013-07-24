/**
 * 
 */
package sinhika.spices.handlers;

import java.util.ArrayList;

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
    private static final int DEFAULT_TYPE_SIZE = EnumToolMaterial.values().length;
    private ArrayList<ToolType> toolTypes;

    public static ToolHelper INSTANCE = new ToolHelper();

    public ToolHelper() {
        toolTypes =  new ArrayList<ToolType>(DEFAULT_TYPE_SIZE);
    }
    
/**
 * loads up the vanilla bark types plus any from this module.
 */
@Override
public void init()
{
    Optional<String> no_string = Optional.absent();
    toolTypes.add(new ToolType("wood", EnumToolMaterial.WOOD, no_string));
    toolTypes.add(new ToolType("stone", EnumToolMaterial.STONE, no_string));
    toolTypes.add(new ToolType("iron", EnumToolMaterial.IRON, no_string));
    toolTypes.add(new ToolType("gold", EnumToolMaterial.GOLD, no_string));
    toolTypes.add(new ToolType("diamond", EnumToolMaterial.EMERALD, no_string));
     
} // end init

public void add(ToolType b) {
    toolTypes.add(b);
}

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

public  String getToolTexture(int index) {
    return toolTypes.get(index).toolTexture;
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
