package cc.zoyn.minebasic.log;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Collection;

/**
 * CustomLogger<br />
 * use {@link ConsoleCommandSender} to custom a logger
 * 使用 {@link ConsoleCommandSender} 来自定义一个Log
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public class Logger {

    private static ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();

    /**
     * you can't construct a {@link Logger}<br />
     * 你不能构造一个{@link Logger}
     */
    private Logger() {
    }

    public static void info(String msg) {
        consoleCommandSender.sendMessage("§7[§2MineBasic - INFO§7] §f" + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void warn(String msg) {
        consoleCommandSender.sendMessage("§7[§eMineBasic - WARN§7] §f" + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void fatal(String msg) {
        consoleCommandSender.sendMessage("§7[§cMineBasic - FATAL§7] §f" + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void error(String msg) {
        consoleCommandSender.sendMessage("§7[§4MineBasic - ERROR§7] §f" + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void debug(String msg) {
        consoleCommandSender.sendMessage("§7[§6MineBasic - DEBUG§7] §f" + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void info(Collection<String> messages) {
        messages.forEach(Logger::info);
    }

    public static void warn(Collection<String> messages) {
        messages.forEach(Logger::warn);
    }

    public static void fatal(Collection<String> messages) {
        messages.forEach(Logger::fatal);
    }

    public static void error(Collection<String> messages) {
        messages.forEach(Logger::error);
    }

    public static void debug(Collection<String> messages) {
        messages.forEach(Logger::debug);
    }
}
