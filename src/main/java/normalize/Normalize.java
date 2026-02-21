package normalize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Normalize {

    NormalizeType type() default NormalizeType.CUSTOM;

    boolean trim() default true;
    boolean collapseSpaces() default false;
    boolean lowerCase() default false;
    boolean upperCase() default false;
    boolean nullIfBlank() default false;
    boolean unicode() default false;
    boolean stripAccents() default false;

    String pattern() default "";
}
