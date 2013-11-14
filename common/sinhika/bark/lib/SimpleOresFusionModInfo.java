/**
 * @file SimpleOresFusionModInfo.java
 * @author cyhiggin
 * @date Nov 14, 2013
 */
package sinhika.bark.lib;

import java.lang.reflect.Field;

import sinhika.bark.handlers.LogHelper;
import net.minecraft.item.EnumToolMaterial;

/**
 * mod info for Simple Ores2 Fusion plug-in
 * 
 */
public class SimpleOresFusionModInfo extends ExternalModInfo
{

    /**
     * 
     */
    public SimpleOresFusionModInfo()
    {
        super("simpleoresfusion", "SimpleOres.plugins.fusion.FusionPlugin",
                3);
        if (modExists)
        {
            toolsExist = initMaterials();
            if (!toolsExist)
            {
                LogHelper.warning("Unable to load tool materials from SimpleOresFusion");
            }
        }
    } // end ctor()

    /*
     * (non-Javadoc)
     * 
     * @see sinhika.bark.lib.ExternalModInfo#initMaterials()
     */
    @Override
    public boolean initMaterials()
    {
        try
        {
            Field toolEnum = mod_class.getField("toolBronze");
            materialList.add((EnumToolMaterial) toolEnum.get(null));
            toolEnum = mod_class.getField("toolThyrium");
            materialList.add((EnumToolMaterial) toolEnum.get(null));
            toolEnum = mod_class.getField("toolSinisite");
            materialList.add((EnumToolMaterial) toolEnum.get(null));
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    } // end initMaterials()

} // end class SimpleOresFusionModInfo
