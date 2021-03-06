/**
 * 
 */
package sinhika.bark.lib;

import com.google.common.base.Optional;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;

/**
 * bundle of all the descriptive data on a tool.
 * @author Sinhika
 *
 */
public class ToolType extends ThingType
{
    public EnumToolMaterial toolMaterial;
 
    /**
     * @param typeName lowercase material name, e.g. 'wood', 'stone', etc.
     * @param toolMaterial tool material from EnumToolMaterial
     * @param toolTexture basename of texture file for tool.
     */
    public ToolType(String typeName, EnumToolMaterial toolMaterial, Optional<String> toolTexture)
    {
        super(typeName);
        this.toolMaterial = toolMaterial;
        this.name = this.typeName + "_spud";
        this.tagLocalized = name + ".name";        
        if (toolTexture.isPresent()) {
            this.texture = toolTexture.get();
        }
        else {
            this.texture =  ModInfo.ID + ":" + typeName + "_spud";
        }
    } // end ToolType ctor

    public Optional<Item> getToolItem()
    {
        return Util.getToolItem(toolMaterial);
    } // end getToolItem
    
    public Optional<Block> getToolBlock()
    {
        return Util.getToolBlock(toolMaterial);
    } // end getToolBlock
    
} // end class
