/**
 * 
 */
package sinhika.spices.lib;

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
    public String toolTexture;  // basename of texture file for tool.
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
            this.toolTexture = toolTexture.get();
        }
        else {
            this.toolTexture =  ModInfo.ID + ":" + typeName + "_spud";
        }
    } // end ToolType ctor

    public Optional<Item> getToolItem()
    {
        Optional<Item> no_item = Optional.absent();
        int id = toolMaterial.getToolCraftingMaterial();
        if (Util.isVanillaBlockID(id))
        {
            return no_item;
        }
        else {
            return Optional.of(Item.itemsList[id]);
        }
    } // end getToolItem
    
    public Optional<Block> getToolBlock()
    {
        Optional<Block> no_block = Optional.absent();
        int id = toolMaterial.getToolCraftingMaterial();
        if (Util.isVanillaBlockID(id))
        {
            return Optional.of(Block.blocksList[id]);
        }
        else {
            return no_block;
        }     
    } // end getToolBlock
    
} // end class
