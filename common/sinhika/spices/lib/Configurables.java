/**
 * 
 */
package sinhika.spices.lib;

import java.util.logging.Level;

import sinhika.spices.handlers.BarkHelper;
import sinhika.spices.handlers.ToolHelper;

/**
 * Holds values loaded from configuration file.
 * @author Sinhika
 *
 */
public class Configurables
{
    public static final String CATEGORY_TOOL = "tools";
    
    public static final int DEFAULT_BASE_ITEMID = 18100;
    public static final int DEFAULT_BLOCKID = 3301;
    public static final String DEFAULT_LOGLEVEL_S = "INFO";
            
    public static final String KEY_LOGLEVEL = "logLevel";
    public static final String KEY_BBID = "barkBlockID";
    public static final String KEY_BI_ID_STEM = "BarkItemID";
    public static final String KEY_TOOL_ID_STEM = "SpudItemID";
    
    public static String keyBarkItemID [] = new String[BarkHelper.INSTANCE.size()];
    public static String keyToolItemID [] = new String[ToolHelper.INSTANCE.size()];
    
    public static Level loggingLevel;
    public static int barkBlockID;    // multi-block bark blockID
    public static int barkItemID[];   // barkItem itemIDs.
    public static int toolItemID[];   // spud tool itemIDs.
}
