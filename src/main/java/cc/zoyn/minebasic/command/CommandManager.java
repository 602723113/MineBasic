package cc.zoyn.minebasic.command;

import cc.zoyn.minebasic.util.SubCommand;
import com.google.common.collect.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * @author Zoyn
 * @since 2017-11-09
 */
public class CommandManager implements ICommandManager, CommandExecutor {

    private static Map<String, SubCommand> commandMap = Maps.newHashMap();

    @Override
    public void registerCommand(String commandName, SubCommand subCommand) {

    }

    @Override
    public void unregisterCommand(String commandName) {

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
