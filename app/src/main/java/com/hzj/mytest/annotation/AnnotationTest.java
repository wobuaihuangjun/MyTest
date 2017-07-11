package com.hzj.mytest.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by hzj on 2017/7/11.
 */

public class AnnotationTest {

    public static void  test(){

        Field[] fields = AnnotationDemo.class.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field.getName());

            Annotation[] annotations = field.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                System.out.println(annotation.toString());
                System.out.println(annotation.annotationType().getSimpleName());
            }

            System.out.println();
        }
    }

}
