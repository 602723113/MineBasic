package cc.zoyn.minebasic.plugin;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent a module<br />
 * 表示一个模块
 *
 * @author Zoyn
 * @since 2017-11-05
 */
@Getter
@Setter
public abstract class Module {

    private ModuleInformation moduleInformation;

    public void onLoad() {
    }

    public void onDisable() {
    }

}
