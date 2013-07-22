package sinhika.spices.handlers;

import java.util.logging.Level;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import sinhika.spices.Spices;
import sinhika.spices.lib.BarkHelper;
import sinhika.spices.lib.Configurables;
import sinhika.spices.lib.Tags;

/** 
 * Bundle up all the configuration loading, setting & saving.
 * @author Sinhika
 *
 */
public class ConfigHelper
{
    public static void init(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        
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
        Configurables.barkItemID = new int[BarkHelper.size()];
        for (int i = 0; i < BarkHelper.size(); i++)
        {
            Configurables.keyBarkItemID[i] = 
                    BarkHelper.getTypeName(i) + Configurables.KEY_BI_ID_STEM;
            
            // TODO add config comments here.
            Configurables.barkItemID[i] = config.getItem( Configurables.keyBarkItemID[i],
                    Configurables.DEFAULT_BASE_ITEMID + i).getInt();
            LogHelper.fine("barkItemID[" + i + "]=" +  Configurables.keyBarkItemID[i] + "="
                    + Configurables.barkItemID[i]);
        }

        // get tool item IDs
        Configurables.toolItemID = new int[Spices.toolTypes.length];
        for (int i = 0; i < Spices.toolTypes.length; i++)
        {
            Configurables.keyToolItemID[i] = 
                    Spices.toolTypes[i] + Configurables.KEY_TOOL_ID_STEM;
            // TODO add config comments here.
            Configurables.toolItemID[i] = config.getItem(Configurables.CATEGORY_TOOL, 
                    Configurables.keyToolItemID[i],
                    Configurables.DEFAULT_BASE_ITEMID + BarkHelper.size() + i)
                    .getInt();
            LogHelper.fine("toolItem[" + i + "]=" + Configurables.keyToolItemID[i] + "="
                    + Configurables.toolItemID[i]);
        }

        // save configuration back to file
        if (config.hasChanged())
        {
            // TODO add log message about saving.
            config.save();
        }
    } // end init()
} // end class
