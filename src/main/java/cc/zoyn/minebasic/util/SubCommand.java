package cc.zoyn.minebasic.util;

import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2017-11-09
 */
public interface SubCommand {

    void execute(CommandSender sender, String[] args);

}
