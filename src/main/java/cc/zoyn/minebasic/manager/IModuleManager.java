package cc.zoyn.minebasic.manager;

import cc.zoyn.minebasic.plugin.Module;

import java.util.List;

/**
 * Standard {@link ModuleManager}<br />
 * 规范 {@link ModuleManager}
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public interface IModuleManager {

    void loadModule(Module module);

    void disableModule(Module module);

    List<Module> getModules();
}
