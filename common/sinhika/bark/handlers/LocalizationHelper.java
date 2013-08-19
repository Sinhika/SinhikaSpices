/**
 * 
 */
package sinhika.bark.handlers;

import sinhika.bark.lib.Localizations;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * @author Sinhika
 * 
 */
public class LocalizationHelper
{
    /**
     * load language localization files into LanguageRegistry.
     */
    public static void loadLanguages()
    {
        // For every file specified in the Localization library class, load them
        // into the Language Registry
        for (String localizationFile : Localizations.localeFiles)
        {
            LanguageRegistry.instance().loadLocalization(localizationFile,
                    getLocaleFromFileName(localizationFile),
                    isXMLLanguageFile(localizationFile));
        }
    } // end loadLanguages()

    /***
     * Simple test to determine if a specified file name represents a XML file
     * or not
     * 
     * @param fileName String representing the file name of the file in question
     * @return True if the file name represents a XML file, false otherwise
     */
    public static boolean isXMLLanguageFile(String fileName)
    {
        return fileName.endsWith(".xml");
    }

    /***
     * Returns the locale from file name
     * 
     * @param fileName String representing the file name of the file in question
     * @return String representation of the locale snipped from the file name
     */
    public static String getLocaleFromFileName(String fileName)
    {

        return fileName.substring(fileName.lastIndexOf('/') + 1,
                fileName.lastIndexOf('.'));
    }

    /**
     * Gets the localized string for a particular tag.
     * @param key non-localized tag to look up
     * @return localized string
     */
    public static String getLocalizedString(String key)
    {

        return LanguageRegistry.instance().getStringLocalization(key);
    }

    /**
     * Gets the localized, formatted string for a particular tag.
     * @param key non-localized tag to look up.
     * @param Object ... args to format
     * @return localized, formatted String.
     */
    public static String getFormattedLocalizedString(String key, Object ...args)
    {
        return String.format(LanguageRegistry.instance().getStringLocalization(key), args);
    }
} // end class
