/**
 * @file ExternalModInfo.java
 * @author cyhiggin
 */
package sinhika.bark.lib;

import java.util.ArrayList;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.EnumToolMaterial;

/**
 * Contains important info from external mods, such as tool material lists.
 *
 */
public abstract class ExternalModInfo 
{
    protected int MOD_TYPE_SIZE = 5;
    protected Class<?> mod_class;   // External mod's item class
    public ArrayList<EnumToolMaterial> materialList;
    public boolean modExists = false;
    public boolean toolsExist = false;
    
    /**
     * Try to load mod item class and flag if successful/unsuccessful.
     * 
     * @param className - name of mod item class to try to load
     * @param default_list_size - expected number of tool materials from mod.
     */
	public ExternalModInfo(String mod_id, String className, int default_list_size) 
	{
	    modExists = Loader.isModLoaded(mod_id);
	    if (! modExists) return;
	        
		try {
    		mod_class = Class.forName(className);
    		modExists = true;
    		MOD_TYPE_SIZE = default_list_size;
    		materialList = new ArrayList<EnumToolMaterial>(MOD_TYPE_SIZE);
    	}
    	catch (ClassNotFoundException e) {
    		modExists = false;
    	}
    	catch (IllegalArgumentException e) {
    		modExists = false;
			e.printStackTrace();
		}
	} // end ctor ExternalModInfo(String, int)

	
	/**
	 * fills in materialList from mod_class. How to do this will differ with
	 * each external mod.
	 * 
	 * @return true if list filled in; false if unable to fill list.
	 */
    public abstract boolean initMaterials();
  
} // end class ExternalModInfo
