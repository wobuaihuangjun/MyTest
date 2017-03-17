package com.hzj.mytest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hzj on 2017/2/22.
 */

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface FiledMode {
    public int value() default 1;
}
