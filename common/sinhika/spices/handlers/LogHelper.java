package sinhika.spices.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import sinhika.spices.lib.ModInfo;
import cpw.mods.fml.common.FMLLog;

/**
 * bundles up logging object & convenience functions.
 * 
 * @author Sinhika
 * 
 */
public class LogHelper
{
    private static Logger spiceLogger = Logger.getLogger(ModInfo.ID);

    public static void init()
    {
        spiceLogger.setParent(FMLLog.getLogger());
    }

    public static void log(Level logLevel, String message)
    {

        spiceLogger.log(logLevel, message);
    }

    public static void severe(String message)
    {
        log(Level.SEVERE, message);
    }

    public static void warning(String message)
    {

        log(Level.WARNING, message);
    }

    public static void info(String message)
    {

        log(Level.INFO, message);
    }

    public static void config(String message)
    {

        log(Level.CONFIG, message);
    }

    public static void fine(String message)
    {

        log(Level.FINE, message);
    }

    public static void finer(String message)
    {

        log(Level.FINER, message);
    }

    public static void finest(String message)
    {

        log(Level.FINEST, message);
    }

    public static void setLevel(Level loggingLevel)
    {
        spiceLogger.setLevel(loggingLevel);
    }

} // end class LogHelper
