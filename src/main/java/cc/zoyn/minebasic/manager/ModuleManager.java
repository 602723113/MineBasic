package cc.zoyn.minebasic.manager;

import cc.zoyn.minebasic.log.Logger;
import cc.zoyn.minebasic.plugin.Module;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Be used for manage modules
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public class ModuleManager implements IModuleManager {

    /**
     * only one instance is allowed to exist
     */
    private static volatile ModuleManager instance;
    /**
     * with modules
     */
    private List<Module> modules = Lists.newArrayList();

    /**
     * you can't construct a {@link ModuleManager},use {@link ModuleManager#getInstance()} to get
     */
    private ModuleManager() {
    }

    /**
     * get ModuleManager's instance
     *
     * @return {@link ModuleManager}
     */
    public static ModuleManager getInstance() {
        if (instance == null) {
            synchronized (ModuleManager.class) {
                if (instance == null) {
                    instance = new ModuleManager();
                }
            }
        }
        return instance;
    }

    @Override
    public void loadModule(Module module) {
        module.onLoad();
        modules.add(module);
        Logger.info("Load Module " + "Successfully");
    }

    @Override
    public void disableModule(Module module) {
        module.onDisable();
        modules.remove(module);
    }

    @Override
    public List<Module> getModules() {
        return modules;
    }
}
