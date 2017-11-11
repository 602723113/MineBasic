package cc.zoyn.minebasic.manager;

import cc.zoyn.minebasic.MineBasic;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Be used for manage config
 *
 * @author Zoyn
 * @since 2017-11-11
 */
public class ConfigManager {

    private static FileConfiguration config = MineBasic.getInstance().getConfig();

    private ConfigManager() {
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static boolean getBooleanByDefault(String key, boolean defaultValue) {
        if (config.contains(key) && config.isBoolean(key)) {
            return config.getBoolean(key);
        }
        return defaultValue;
    }

    public static String getStringByDefault(String key, String defaultValue) {
        if (config.contains(key) && config.isString(key)) {
            return config.getString(key);
        }
        return defaultValue;
    }

    public static int getIntByDefault(String key, int defaultValue) {
        if (config.contains(key) && config.isInt(key)) {
            return config.getInt(key);
        }
        return defaultValue;
    }

    public static double getDoubleByDefault(String key, double defaultValue) {
        if (config.contains(key) && config.isDouble(key)) {
            return config.getDouble(key);
        }
        return defaultValue;
    }

    public static long getLongByDefault(String key, long defaultValue) {
        if (config.contains(key) && config.isLong(key)) {
            return config.getLong(key);
        }
        return defaultValue;
    }
}
