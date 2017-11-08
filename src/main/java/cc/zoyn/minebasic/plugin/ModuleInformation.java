package cc.zoyn.minebasic.plugin;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represent a module's information<br />
 * 表示一个模块的信息
 *
 * @author Zoyn
 * @since 2017-11-05
 */
@Data
@AllArgsConstructor
public class ModuleInformation {

    private String name;
    private String main;
    private String version;
    private String author;

}
