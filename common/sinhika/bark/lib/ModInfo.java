package sinhika.bark.lib;

public class ModInfo
{
    public static final String ID = "sinhikabark";
    public static final String NAME = "Sinhika's Bark";
    public static final String VERSION = "@VERSION@ (build @BUILD_NUMBER@)";
    public static final String CHANNEL = ID;

    public static boolean hasSimpleOres = false;
    public static boolean hasSimpleOresFusion = false;
    public static SimpleOresModInfo so2_info;
    public static SimpleOresFusionModInfo so2fusion_info;

// public static boolean hasMetallurgy = false;

    /**
     * identify other tool-material mods if they are available, and get their
     * tool-material lists.
     */
    public static void findMods()
    {
        // Simple Ores2 & possible plug-ins
        so2_info = new SimpleOresModInfo();
        hasSimpleOres = so2_info.modExists;
        so2fusion_info = new SimpleOresFusionModInfo();
        hasSimpleOresFusion = so2fusion_info.modExists;
    } // end findMods()

} // end class ModInfo
