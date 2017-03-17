package com.hzj.mytest.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by huangzj on 2016/3/17.
 */
public class JavaDomain {

    public static void main(String[] args) {

        Field[] fields = AnnotationDemo.class.getDeclaredFields();
        Field field = null;
        for (Field f : fields) {
            if (f.getName().equals("itest")) {
                field = f;
                break;
            }
        }

        Annotation[] annotations = field.getDeclaredAnnotations();

        for (Annotation annotation : annotations) {
            System.out.println(annotation.toString());
            System.out.println(annotation.annotationType().getSimpleName());
        }

    }


}
