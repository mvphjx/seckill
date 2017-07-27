package com.han.util.json;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class TextInfoUtil
{

    private static final Logger LOG = LoggerFactory.getLogger(TextInfoUtil.class);

    public static TextInfoUtil createJsonTextInfoUtil()
    {
        return new JsonTextInfoUtil(null);
    }

    public static TextInfoUtil createJsonTextInfoUtil(String textInfo)
    {
        return new JsonTextInfoUtil(textInfo);
    }

    public static TextInfoUtil createTextInfoUtil(String textInfo, TextInfoFormat txtInfoFormat)
    {
        if (txtInfoFormat == TextInfoFormat.JSON)
            return new JsonTextInfoUtil(textInfo);
        LOG.error("unsupported format of text information.");
        return null;
    }

    public abstract Iterator<String> getAllFields();

    public abstract Iterator<String> getAllFields(JsonNode jn);

    public abstract String getString(String fieldName);

    public abstract JsonNode getObject(String fieldName);

    public abstract int getInt(String fieldName);

    public abstract long getLong(String fieldName);

    public abstract boolean isObject(String fieldName);

    public abstract boolean isArray(String fieldName);

    public abstract boolean isInt(String fieldName);

    public abstract boolean isString(String fieldName);

    public abstract List<JsonNode> getArrayObject(String fieldName);

    public abstract String getString(JsonNode jNode, String fieldName);

    public abstract int getInt(JsonNode jNode, String fieldName);

    public abstract long getLong(JsonNode jNode, String fieldName);

    public abstract void addStringValue(String key, String value);

    public abstract void addIntValue(String key, int value);

    public abstract void addLongValue(String key, long value);

    public abstract Map<String, Object> createJsonObject(String text);

    public abstract Map<String, Object> createJsonObject();

    public abstract void addStringValue(Map<String, Object> objMap, String key, String value);

    public abstract void addIntValue(Map<String, Object> objMap, String key, int value);

    public abstract void addLongValue(Map<String, Object> objMap, String key, long value);

    public abstract void addObjectValue(String key, Map<String, Object> objMap);

    public abstract void addObject(String key, Object objMap);

    public abstract void addArrayElement(String key, Map<String, Object> objMap);

    public abstract String writeToString();

    public abstract String writeToString(Map<String, Object> objMap);

    // extended methods
    public abstract Object convertToObject(Class<?> clazz);

    public abstract Object convertToObject(String txtInfo, Class<?> clazz);

    public abstract String convertToString(Object obj);

    public abstract Object mergeTo(Class<?> clazz, Object targetObj);

}
