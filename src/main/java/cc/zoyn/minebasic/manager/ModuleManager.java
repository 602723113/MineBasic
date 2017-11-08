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
     * only one instance is allowed to exist<br />
     * 只允许一个实例存在
     */
    private static volatile ModuleManager instance;
    /**
     * with modules<br />
     * 存放 Modules
     */
    private List<Module> modules = Lists.newArrayList();

    /**
     * you can't construct a {@link ModuleManager},use {@link ModuleManager#getInstance()} to get <br />
     * 你不能实例化一个 {@link ModuleManager}, 请使用 ModuleManager.getInstance() 获得
     */
    private ModuleManager() {
    }

    /**
     * get ModuleManager's instance<br />
     * 获取 ModuleManager 的实例
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
        Logger.info("[" + module.getModuleInformation().getName() + "] Load module successfully");
    }

    @Override
    public void disableModule(Module module) {
        module.onDisable();
        modules.remove(module);
        Logger.info("[" + module.getModuleInformation().getName() + "] Disable module successfully");
    }

    @Override
    public List<Module> getModules() {
        return modules;
    }
}
