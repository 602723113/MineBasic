package cc.zoyn.minebasic.plugin;

/**
 * Represent a module
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public abstract class Module {

    abstract String getName();

    abstract String getDescription();

    abstract String getVersion();

    abstract String getAuthor();

    public void onLoad() {
    }

    public void onDisable() {
    }

}
