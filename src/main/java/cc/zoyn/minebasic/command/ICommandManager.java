package cc.zoyn.minebasic.command;

import cc.zoyn.minebasic.util.SubCommand;

/**
 * @author Zoyn
 * @since 2017-11-09
 */
public interface ICommandManager {

    void registerCommand(String commandName, SubCommand subCommand);

    void unregisterCommand(String commandName);

}
