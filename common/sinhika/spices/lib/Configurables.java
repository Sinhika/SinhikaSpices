/**
 * 
 */
package sinhika.spices.lib;

import java.util.ArrayList;
import java.util.logging.Level;

import sinhika.spices.handlers.BarkHelper;
import sinhika.spices.handlers.SpiceHelper;

/**
 * Holds values loaded from configuration file.
 * @author Sinhika
 *
 */
public class Configurables
{
    public static final String CATEGORY_TOOL = "tools";
    public static final String CATEGORY_BARK = "bark";
    
    public static final int DEFAULT_BASE_ITEMID = 18100;
    public static final int DEFAULT_BASE_BARKID = 18200;
    public static final int DEFAULT_BASE_TOOLID = 18300;
    public static final int DEFAULT_BLOCKID = 3301;
    public static final String DEFAULT_LOGLEVEL_S = "INFO";
            
    public static final String KEY_LOGLEVEL = "logLevel";
    public static final String KEY_BBID = "barkBlockID";
    public static final String KEY_BI_ID_STEM = "BarkItemID";
    public static final String KEY_TOOL_ID_STEM = "SpudItemID";
    public static final String KEY_SPICE_ID_STEM = "SpiceItemID";
    
    public static String keyBarkItemID [] = new String[BarkHelper.INSTANCE.size()];
    public static ArrayList<String> keyToolItemID;
    public static String keySpiceItemID [] = new String[SpiceHelper.INSTANCE.size()];
    public static String keyCanoeItemID;
    
    public static Level loggingLevel;
    public static int barkBlockID;    // multi-block bark blockID
    public static int barkItemID[];   // barkItem itemIDs.
    public static ArrayList<Integer>toolItemID;   // spud tool itemIDs.
    public static int spiceItemID[];    // spiceItem ids
    public static int canoeItemID;
} // end class
