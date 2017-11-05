package cc.zoyn.minebasic;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main Class
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public class MineBasic extends JavaPlugin {

    private static MineBasic instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    /**
     * 取MineBasic的实例对象<br />
     * get MineBasic's instance
     *
     * @return {@link MineBasic}
     */
    public static MineBasic getInstance() {
        return instance;
    }

}
