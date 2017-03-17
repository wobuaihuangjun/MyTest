package com.hzj.mytest.util;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * JSON转换工具类
 *
 * @author lhd
 */
public class JSONUtil {

    private static String TAG = JSONUtil.class.getName();

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        //对于为null的字段不进行序列化
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //对于未知属性不进行反序列化
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //无论对象中的值只有不为null的才进行序列化
        mapper.setSerializationInclusion(Include.NON_NULL);

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    /**
     * 把对象转化成json字符串
     *
     * @param obj
     * @return
     */
    public static String toJSON(Object obj) {
        if (obj == null) {
            return null;
        }

        Writer write = new StringWriter();
        try {
            mapper.writeValue(write, obj);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return write.toString();
    }

    /**
     * JSON字符串转成对象
     *
     * @param jsonStr
     * @param classType
     * @return
     */
    public static <T> T fromJSON(String jsonStr, Class<T> classType) {
        if (StringUtils.isEmptyOrNull(jsonStr)) {
            return null;
        }

        T t = null;
        try {
            t = mapper.readValue(jsonStr.getBytes("utf-8"), classType);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * JSON字符串转化成集合
     *
     * @param jsonStr
     * @return
     */
    public static <T> T toCollection(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) {
        if (StringUtils.isEmptyOrNull(jsonStr)) {
            return null;
        }

        T t = null;
        try {
            t = mapper.readValue(jsonStr.getBytes("utf-8")
                    , mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses));
        } catch (JsonParseException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static Object get(String jsonStr, String key) {
        Object obj = null;
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            obj = jsonObj.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
