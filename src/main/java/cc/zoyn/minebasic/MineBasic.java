package cc.zoyn.minebasic;

import cc.zoyn.minebasic.exception.UnLoadableModuleException;
import cc.zoyn.minebasic.manager.ModuleManager;
import cc.zoyn.minebasic.plugin.Module;
import cc.zoyn.minebasic.plugin.ModuleClassLoader;
import cc.zoyn.minebasic.plugin.ModuleInformation;
import cc.zoyn.minebasic.util.FileNameFilter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

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
    public static ModuleManager getMouduleManager() {
        return ModuleManager.getInstance();
    }

    public static File getModuleFile() {
        return moduleFile;
    }

    private void loadModules() throws UnLoadableModuleException {
        ModuleManager moduleManager = getMouduleManager();
        File[] moduleJars = moduleFile.listFiles(new FileNameFilter(".jar"));
        if (moduleJars == null)
            return;

        URL url;
        InputStreamReader inputStreamReader = null;
        FileConfiguration fileConfiguration;

        String className;
        Class<? extends Module> moduleClass;
        Module module = null;
        for (File moduleJar : moduleJars) {
            try {
                url = new URL("jar:file:/" + moduleJar.getAbsolutePath() + "!/module.yml");
                inputStreamReader = new InputStreamReader(url.openStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (inputStreamReader == null) {
                throw new UnLoadableModuleException("can't load module.yml!");
            }

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


            try {
                ModuleInformation moduleInformation = new ModuleInformation(fileConfiguration.getString("name"), fileConfiguration.getString("main"), fileConfiguration.getString("version"), fileConfiguration.getString("author"));
                className = moduleInformation.getMain();

                // load class...
//                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
//                method.setAccessible(true);

                // 获取系统类加载器
                ModuleClassLoader moduleClassLoader = ModuleClassLoader.getInstance();
                moduleClassLoader.loadJar(moduleJar.toURI().toURL());

//                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
//                method.invoke(classLoader, moduleJar.toURI().toURL());

                System.out.println("加载成功");
                Class classObj = Class.forName("cc.zoyn.spawn.Spawn");
                classObj.getMethod("onLoad").invoke(classObj);
//                moduleClass = Class.forName(className).asSubclass(Module.class);


//                moduleClass = Class.forName("cc.zoyn.spawn.Spawn", true, classLoader);
//                module = (Module) moduleClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (module == null)
                throw new UnLoadableModuleException("can't load this module");

            moduleManager.loadModule(module);
        }
    }
}
