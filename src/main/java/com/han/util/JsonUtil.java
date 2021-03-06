package com.han.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    /**
     * jackson实现object转json
     * @param obj
     * @return String
     */
    public static String createJsDataByJackson(Object obj)
    {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try
        {
            result = mapper.writeValueAsString(obj);
        }
        catch (JsonGenerationException e)
        {
            // TODO 异常处理
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * jackson实现object转json
     * @param valueType
     * @return String
     */
    public static <T> T createObjectByJackson(String json, Class<T> valueType)
    {

        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try
        {
            t = (T) mapper.readValue(json,valueType);
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return t;
    }
    public static void main(String[] args) {
        //jackson有很多强大的注解，可以规定json格式，json解析策略
        // jackson annotations注解详解

    }

}
