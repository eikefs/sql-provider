package io.github.eikefs.sql.provider.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {

    boolean nullable() default true;
    boolean unique() default false;
    String type() default "varchar";
    int size() default -1;

}
