package sinhika.spices.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import sinhika.spices.lib.Configurables;
import sinhika.spices.lib.Tags;

/** 
 * Bundle up all the configuration loading, setting & saving.
 * @author Sinhika
 *
 */
public class ConfigHelper
{
    private static Configuration config;
    
    public static void init(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        
        // load configuration from file
        config.load();
        LogHelper.info(
                LocalizationHelper.getFormattedLocalizedString(
                        Tags.FMT_MSG_READ_LOGLEVEL, 
                        event.getSuggestedConfigurationFile()));

        // get logging level
        Property logProp = config.get(Configuration.CATEGORY_GENERAL, 
                                      Configurables.KEY_LOGLEVEL, 
                                      Configurables.DEFAULT_LOGLEVEL_S);
        logProp.comment = LocalizationHelper.getLocalizedString(Tags.COMMENT_LOGLEVEL);
        try
        {
            Configurables.loggingLevel = Level.parse(logProp.getString());
        }
        catch (IllegalArgumentException e)
        {
            Configurables.loggingLevel = Level.ALL;
            LogHelper.warning(LocalizationHelper.getLocalizedString(Tags.MSG_BAD_LOGLEVEL));
            logProp.set(Configurables.loggingLevel.toString());
        }
        
        LogHelper.setLevel(Configurables.loggingLevel);
        LogHelper.info(
                LocalizationHelper.getFormattedLocalizedString(
                        Tags.FMT_MSG_SET_LOGLEVEL, 
                        Configurables.loggingLevel.toString()));
        
        // get block IDs
        Configurables.barkBlockID = 
                config.getBlock(Configurables.KEY_BBID,
                        Configurables.DEFAULT_BLOCKID, 
                        LocalizationHelper.getLocalizedString(Tags.COMMENT_BARKBLOCKID)).getInt();
        LogHelper.fine(Configurables.KEY_BBID + "=" + Configurables.barkBlockID);

        // get item IDs
        Configurables.spiceItemID = new int[SpiceHelper.INSTANCE.size()];
        for (int i=0; i < SpiceHelper.INSTANCE.size(); i++)
        {
            Configurables.keySpiceItemID[i] =
                    SpiceHelper.INSTANCE.getTypeName(i) + Configurables.KEY_SPICE_ID_STEM;
            // TODO add config comments here.
            Configurables.spiceItemID[i] = 
                    config.getItem(Configurables.keySpiceItemID[i],
                                   Configurables.DEFAULT_BASE_ITEMID - (i+1)).getInt();
            LogHelper.fine("spiceItemID[" + i + "]=" +  Configurables.keySpiceItemID[i] + "="
                    + Configurables.spiceItemID[i]);
        }
        Configurables.keyCanoeItemID = "birchCanoeItemID";
        Configurables.canoeItemID = 
                config.getItem(Configurables.keyCanoeItemID, 
                               Configurables.DEFAULT_BASE_ITEMID - (SpiceHelper.INSTANCE.size() + 1)).getInt();
                
        Configurables.barkItemID = new int[BarkHelper.INSTANCE.size()];
        for (int i = 0; i < BarkHelper.INSTANCE.size(); i++)
        {
            Configurables.keyBarkItemID[i] = 
                    BarkHelper.INSTANCE.getTypeName(i) + Configurables.KEY_BI_ID_STEM;
            
            // TODO add config comments here.
            Configurables.barkItemID[i] = config.getItem( Configurables.keyBarkItemID[i],
                    Configurables.DEFAULT_BASE_ITEMID + i).getInt();
            LogHelper.fine("barkItemID[" + i + "]=" +  Configurables.keyBarkItemID[i] + "="
                    + Configurables.barkItemID[i]);
        }

        // TOOLS
        // we might have surprise new tool types because of other mods,
        // so check what all is in the tool category.
        ConfigCategory toolCat = config.getCategory(Configurables.CATEGORY_TOOL);
        int toolCount = Math.max(toolCat.size(), ToolHelper.INSTANCE.size());
        
        Configurables.toolItemID = new ArrayList<Integer>(toolCount);
        Configurables.keyToolItemID = new ArrayList<String>(toolCount);
        
        // first, get the knowns.
        HashSet<String> keySet = new HashSet<String>(toolCat.keySet());
                
        for (int i = 0; i < ToolHelper.INSTANCE.size(); i++)
        {
            Configurables.keyToolItemID.add( 
                    ToolHelper.INSTANCE.getTypeName(i) + Configurables.KEY_TOOL_ID_STEM);
            // TODO add config comments here.
            Configurables.toolItemID.add( 
                    config.getItem(Configurables.CATEGORY_TOOL, 
                                    Configurables.keyToolItemID.get(i),
                    Configurables.DEFAULT_BASE_ITEMID + BarkHelper.INSTANCE.size() + i)
                    .getInt());
            LogHelper.fine("toolItem[" + i + "]=" + Configurables.keyToolItemID.get(i) + "="
                    + Configurables.toolItemID.get(i));
            
            if (keySet.contains(Configurables.keyToolItemID.get(i))) 
            {
                keySet.remove(Configurables.keyToolItemID.get(i));
            }
        }
        // then, check for extras
        if (!keySet.isEmpty())
        {
            int i = ToolHelper.INSTANCE.size();
            for (String k : keySet)
            {
                Configurables.keyToolItemID.add(k);
                Configurables.toolItemID.add( 
                    config.getItem(Configurables.CATEGORY_TOOL, k,
                                   Configurables.DEFAULT_BASE_ITEMID 
                                    + BarkHelper.INSTANCE.size() + i).getInt());
                LogHelper.fine("toolItem[" + i + "]=" + Configurables.keyToolItemID.get(i) 
                        + "=" + Configurables.toolItemID.get(i));                
                i++;
            }
        } // end-if keySet not empty
        
        // save configuration back to file
        if (config.hasChanged())
        {
            // TODO add log message about saving.
            config.save();
        }
    } // end init()
    
    public static int addTool(EnumToolMaterial tm)
    {
        int i = ToolHelper.INSTANCE.materialsInUse.get(tm);
        Configurables.keyToolItemID.add(ToolHelper.INSTANCE.getTypeName(i) + Configurables.KEY_TOOL_ID_STEM);
        Configurables.toolItemID.add( 
                config.getItem(Configurables.CATEGORY_TOOL, 
                                Configurables.keyToolItemID.get(i),
                Configurables.DEFAULT_BASE_ITEMID + BarkHelper.INSTANCE.size() + i).getInt());
        LogHelper.fine("toolItem[" + i + "]=" + Configurables.keyToolItemID.get(i) 
                + "=" + Configurables.toolItemID.get(i));
        config.save();
        return Configurables.toolItemID.get(i);
    } // end addTool()
    
} // end class
