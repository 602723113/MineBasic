package cc.zoyn.minebasic;

import cc.zoyn.minebasic.exception.UnLoadableModuleException;
import cc.zoyn.minebasic.manager.ModuleManager;
import cc.zoyn.minebasic.plugin.Module;
import cc.zoyn.minebasic.util.FileNameFilter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
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

        InputStreamReader inputStreamReader = null;
        FileConfiguration fileConfiguration;
        
        Class<?> moduleClass;
        Module module = null;
        for (int i = 0; i < moduleJars.length; i++) {
            try {
                URL url = new URL("jar:file:" + moduleJars[i].getAbsolutePath() + "!/module.yml");
                inputStreamReader = new InputStreamReader(url.openStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (inputStreamReader == null) {
                throw new UnLoadableModuleException("can't load module.yml!");
            }

            // check module.yml datas
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
                moduleClass = Class.forName(fileConfiguration.getString("main"));

                ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                classLoader.loadClass(moduleClass.getName());

                System.out.println(moduleClass.getName());
                module = (Module) moduleClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (module == null)
                throw new UnLoadableModuleException("can't load this module");

            moduleManager.loadModule(module);
        }
    }
}
