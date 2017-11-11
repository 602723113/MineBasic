package cc.zoyn.minebasic;

import cc.zoyn.minebasic.command.CommandManager;
import cc.zoyn.minebasic.exception.UnLoadableModuleException;
import cc.zoyn.minebasic.manager.ModuleManager;
import cc.zoyn.minebasic.plugin.Module;
import cc.zoyn.minebasic.plugin.ModuleClassLoader;
import cc.zoyn.minebasic.plugin.ModuleInformation;
import cc.zoyn.minebasic.util.FileNameFilter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Main Class
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public class MineBasic extends JavaPlugin {

    private static MineBasic instance;
    private static File moduleFile;

    @Override
    public void onEnable() {
        instance = this;

        moduleFile = new File(instance.getDataFolder(), "Modules");
        if (!moduleFile.exists())
            moduleFile.mkdirs();

        try {
            loadModules();
        } catch (UnLoadableModuleException e) {
            e.printStackTrace();
        }

        Bukkit.getPluginCommand("minebasic").setExecutor(new CommandManager());
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

    /**
     * 取ModuleManager的实例对象<br />
     * get ModuleManager's instance
     *
     * @return {@link ModuleManager}
     */
    public static ModuleManager getModuleManager() {
        return ModuleManager.getInstance();
    }

    public static File getModuleFile() {
        return moduleFile;
    }

    /**
     * load in the plugins/MineBasic/Modules Modules
     * 读取 plugins/MineBasic/Modules 里的所有模块
     *
     * @throws UnLoadableModuleException
     */
    private void loadModules() throws UnLoadableModuleException {
        ModuleManager moduleManager = getModuleManager();
        File[] moduleJars = moduleFile.listFiles(new FileNameFilter(".jar"));
        if (moduleJars == null)
            return;

        JarFile jarFile = null;
        InputStreamReader inputStreamReader = null;
        FileConfiguration fileConfiguration;
        ModuleInformation moduleInformation = null;
        ModuleClassLoader moduleClassLoader = ModuleClassLoader.getInstance();

        String className;
        Class<? extends Module> moduleClass;
        Module module = null;
        for (File moduleJar : moduleJars) {
            try {
                jarFile = new JarFile(moduleJar.getAbsolutePath());
                JarEntry jarEntry = jarFile.getJarEntry("module.yml");
                if (jarEntry == null)
                    throw new UnLoadableModuleException("jar does not contain module.yml!");
                inputStreamReader = new InputStreamReader(jarFile.getInputStream(jarEntry));

                // check module.yml data
                fileConfiguration = YamlConfiguration.loadConfiguration(inputStreamReader);
                Set<String> keys = fileConfiguration.getKeys(false);
                if (!keys.contains("name"))
                    throw new UnLoadableModuleException("module.yml doesn't have name!");
                if (!keys.contains("main"))
                    throw new UnLoadableModuleException("module.yml doesn't have main!");
                if (!keys.contains("version"))
                    throw new UnLoadableModuleException("module.yml doesn't have version!");
                if (!keys.contains("author"))
                    throw new UnLoadableModuleException("module.yml doesn't have author!");
                moduleInformation = new ModuleInformation(fileConfiguration.getString("name"), fileConfiguration.getString("main"), fileConfiguration.getString("version"), fileConfiguration.getString("author"));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // close streams
                if (jarFile != null) {
                    try {
                        jarFile.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                }
            }

            if (moduleInformation == null)
                throw new UnLoadableModuleException("can't load module.yml!");
            className = moduleInformation.getMain();

            try {
                // load class...
                moduleClassLoader.loadJar(moduleJar.toURI().toURL());

                // check 'module' main class
                Class<?> jarClass;
                try {
                    jarClass = Class.forName(className);
                } catch (ClassNotFoundException ex) {
                    throw new UnLoadableModuleException("Cannot find main class `" + className + "'", ex);
                }

                try {
                    moduleClass = jarClass.asSubclass(Module.class);
                } catch (ClassCastException ex) {
                    throw new UnLoadableModuleException("main class `" + className + "' does not extend Module", ex);
                }

                module = moduleClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (module == null)
                throw new UnLoadableModuleException("can't load this module");

            module.setModuleInformation(moduleInformation);

            // give the obj to manager
            moduleManager.loadModule(module);
        }
    }
}
