package cc.zoyn.minebasic.plugin;

import cc.zoyn.minebasic.MineBasic;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Use to load a module's jar<br />
 * 用于读取一个模块的jar包
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public class ModuleClassLoader extends URLClassLoader {

    private static ModuleClassLoader instance;
    private static URLClassLoader classLoader = (URLClassLoader) MineBasic.class.getClassLoader();
    private static final Method ADD_URL = initAddMethod();

    static {
        ClassLoader.registerAsParallelCapable();
    }

    private ModuleClassLoader(URL[] urls) {
        super(urls);
    }

    public static ModuleClassLoader getInstance() {
        if (instance == null) {
            instance = new ModuleClassLoader(new URL[]{});
        }
        return instance;
    }

    private static Method initAddMethod() {
        try {
            Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addUrl.setAccessible(true);
            return addUrl;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadJar(URL url) {
        try {
            ADD_URL.invoke(classLoader, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
