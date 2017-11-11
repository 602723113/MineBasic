package cc.zoyn.minebasic.command;

import cc.zoyn.minebasic.command.subcommand.ModulesCommand;
import cc.zoyn.minebasic.log.Logger;
import cc.zoyn.minebasic.manager.ConfigManager;
import cc.zoyn.minebasic.util.SubCommand;
import com.google.common.collect.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * To manage MineBasic's commands
 *
 * @author Zoyn
 * @since 2017-11-09
 */
public class CommandManager implements ICommandManager, CommandExecutor {

    private static Map<String, SubCommand> commandMap = Maps.newHashMap();

    /**
     * initial all sub command
     */
    public CommandManager() {
        registerCommand("modules", new ModulesCommand());
    }

    @Override
    public void registerCommand(String commandName, SubCommand subCommand) {
        if (commandMap.containsKey(commandName)) {
            Logger.warn("duplicate add command!");
        }
        commandMap.put(commandName, subCommand);
    }

    @Override
    public void unregisterCommand(String commandName) {
        if (commandMap.containsKey(commandName)) {
            commandMap.remove(commandName);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ConfigManager.getStringByDefault("CommandMessages", "&7---- &6[&eMineBasic&6] &7----\n&b/mb modules &7check all modules").replaceAll("&", "§"));
            return true;
        }
        if (!commandMap.containsKey(args[0])) {
            sender.sendMessage("§cunknown command!");
            return true;
        }
        // args[0] ---> SubCommand
        SubCommand subCommand = commandMap.get(args[0]);
        subCommand.execute(sender, args);
        return true;
    }
}
