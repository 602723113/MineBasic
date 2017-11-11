package cc.zoyn.minebasic.command.subcommand;

import cc.zoyn.minebasic.manager.ModuleManager;
import cc.zoyn.minebasic.plugin.ModuleInformation;
import cc.zoyn.minebasic.util.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2017-11-11
 */
public class ModulesCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        ModuleManager.getInstance().getModules().forEach(
                module -> {
                    sender.sendMessage(module.getModuleInformation().getName() + " - " + module.getModuleInformation().getVersion());
                }
        );

    }

}
