/**
 * @file SimpleOresModInfo.java
 * @author cyhiggin
 * @date Nov 14, 2013
 */
package sinhika.bark.lib;

import java.lang.reflect.Field;

import sinhika.bark.handlers.LogHelper;
import net.minecraft.item.EnumToolMaterial;

/**
 * @author cyhiggin
 * 
 */
public class SimpleOresModInfo extends ExternalModInfo
{

    /**
     * default constructor
     */
    public SimpleOresModInfo()
    {
        super("simpleores", "SimpleOres.core.SimpleOres", 5);
        if (modExists)
        {
            toolsExist = initMaterials();
            if (!toolsExist)
            {
                LogHelper.warning("Unable to load tool materials from SimpleOres");
            }
        }
    } // end ctor SimpleOresModInfo()

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
            Field toolEnum = mod_class.getField("toolCopper");
            materialList.add((EnumToolMaterial) toolEnum.get(null));
            toolEnum = mod_class.getField("toolTin");
            materialList.add((EnumToolMaterial) toolEnum.get(null));
            toolEnum = mod_class.getField("toolMythril");
            materialList.add((EnumToolMaterial) toolEnum.get(null));
            toolEnum = mod_class.getField("toolAdamantium");
            materialList.add((EnumToolMaterial) toolEnum.get(null));
            toolEnum = mod_class.getField("toolOnyx");
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

} // end class SimpleOresModInfo
