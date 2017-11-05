package cc.zoyn.minebasic.annotation;

import java.lang.annotation.*;


/**
 * @author Zoyn
 * @since 2017-11-05
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    String name();

    String main();

    String version();

    String author();
}
