package io.github.eikefs.sql.provider.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {

    boolean nullable() default false;
    boolean unique() default false;
    String sqlType() default "varchar";
    int size() default 255;

}
