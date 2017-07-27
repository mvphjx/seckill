package com.han.util.json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class JsonTextInfoUtil extends TextInfoUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(JsonTextInfoUtil.class);
    private String textInfo;
    private JsonNode jsonRoot = null;
    private Map<String, Object> jsonMap;
    private ObjectMapper mapper;

    public JsonTextInfoUtil(String txtInfo)
    {
        this.textInfo =txtInfo==null? "{}" : txtInfo;

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
        mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule("Timestamp", Version.unknownVersion());
        module.addDeserializer(Timestamp.class, new TimestampDeserializer());
        mapper.registerModule(module);

        SimpleModule module2 = new SimpleModule("LocalDateTime", Version.unknownVersion());
        module2.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer());
        module2.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(module2);

        mapper.setSerializationInclusion(Inclusion.NON_NULL);
        mapper.setDateFormat(df);

        reparseJsonString();
        jsonMap = new HashMap<>();
    }

    private void reparseJsonString()
    {
        if (textInfo==null)
            return;
        try
        {
            jsonRoot = mapper.readTree(textInfo);
            jsonMap = mapper.readValue(textInfo, new TypeReference<Map<String, Object>>()
            {
            });
        }
        catch (IOException e)
        {
            LOG.error("cannot find the resource for customized text information - " + e.getMessage());
        }
    }

    private void refresh()
    {
        textInfo = writeToString();
        reparseJsonString();
    }

    @Override
    public Iterator<String> getAllFields()
    {
        return getAllFields(jsonRoot);
    }

    @Override
    public Iterator<String> getAllFields(JsonNode jn)
    {
        return jn != null ? jn.getFieldNames() : (new ArrayList<String>()).iterator();
    }

    @Override
    public String getString(String fieldName)
    {
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return null;
        }

        JsonNode field = jsonRoot.get(fieldName);
        if (field == null)
        {
            LOG.debug("get field failed - " + fieldName);
            return null;
        }
        return field.asText();
    }

    @Override
    public JsonNode getObject(String fieldName)
    {
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return null;
        }
        JsonNode field = jsonRoot.get(fieldName);
        if (field == null)
        {
            LOG.debug("get field failed - " + fieldName);
            return null;
        }
        return field;
    }

    @Override
    public int getInt(String fieldName)
    {
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return -1;
        }
        JsonNode field = jsonRoot.get(fieldName);
        if (field == null)
        {
            LOG.debug("get field failed - " + fieldName);
            return -1;
        }
        return jsonRoot.get(fieldName).asInt();
    }

    @Override
    public long getLong(String fieldName)
    {
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return -1;
        }
        return jsonRoot.get(fieldName).asLong();
    }

    @Override
    public boolean isObject(String fieldName)
    {
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return false;
        }
        return jsonRoot.get(fieldName).isObject();
    }

    @Override
    public boolean isArray(String fieldName)
    {
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return false;
        }
        return jsonRoot.get(fieldName) != null && jsonRoot.get(fieldName).isArray();
    }

    @Override
    public boolean isInt(String fieldName)
    {
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return false;
        }
        return jsonRoot.get(fieldName) != null && jsonRoot.get(fieldName).isInt();
    }

    @Override
    public boolean isString(String fieldName)
    {
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return false;
        }
        return jsonRoot.get(fieldName) != null && jsonRoot.get(fieldName).isTextual();
    }

    @Override
    public List<JsonNode> getArrayObject(String fieldName)
    {
        List<JsonNode> arrayObj = new ArrayList<>();
        if (jsonRoot==null)
        {
            LOG.error("jsonRoot is empty");
            return arrayObj;
        }
        JsonNode arrayElement = jsonRoot.get(fieldName);
        if ( arrayElement == null ) return null;
        if (arrayElement.isArray())
        {
            Iterator<JsonNode> it = arrayElement.getElements();
            while (it.hasNext())
            {
                arrayObj.add(it.next());
            }
        }
        return arrayObj;
    }

    @Override
    public String getString(JsonNode jNode, String fieldName)
    {
        JsonNode jn = jNode != null ? jNode.get(fieldName) : null;
        return jn != null ? jn.asText() : null;
    }

    @Override
    public int getInt(JsonNode jNode, String fieldName)
    {
        return jNode != null ? jNode.get(fieldName).asInt() : -1;
    }

    @Override
    public long getLong(JsonNode jNode, String fieldName)
    {
        return jNode != null ? jNode.get(fieldName).asLong() : -1;
    }

    @Override
    public void addStringValue(String key, String value)
    {
        jsonMap.put(key, value);
        refresh();
    }

    @Override
    public void addIntValue(String key, int value)
    {
        jsonMap.put(key, value);
        refresh();
    }

    @Override
    public void addLongValue(String key, long value)
    {
        jsonMap.put(key, value);
        refresh();
    }

    @Override
    public void addStringValue(Map<String, Object> objMap, String key, String value)
    {
        objMap.put(key, value);
    }

    @Override
    public void addIntValue(Map<String, Object> objMap, String key, int value)
    {
        objMap.put(key, value);
    }

    @Override
    public void addLongValue(Map<String, Object> objMap, String key, long value)
    {
        objMap.put(key, value);
    }

    @Override
    public Map<String, Object> createJsonObject(String text)
    {
        String root = text==null ? "{}" : text;

        try
        {
            Map<String, Object> map;
            // convert JSON string to Map
            map = mapper.readValue(root, new TypeReference<Map<String, Object>>()
            {
            });
            return map;
        }
        catch (IOException e)
        {
            LOG.error("createJsonObject failed" + e.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Object> createJsonObject()
    {
        return createJsonObject(null);
    }

    @Override
    public void addObjectValue(String key, Map<String, Object> objMap)
    {
        jsonMap.put(key, objMap);
        refresh();
    }

    @Override
    public void addObject(String key, Object objMap)
    {
        jsonMap.put(key, objMap);
        refresh();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addArrayElement(String key, Map<String, Object> objMap)
    {
        if (objMap.isEmpty())
            return;
        if (jsonMap.containsKey(key))
        {
            Object arr = jsonMap.get(key);
            if (arr instanceof List<?>)
            {
                ((List) arr).add(objMap);
            }
        }
        else
        {
            List<Object> array = new ArrayList<>();
            array.add(objMap);
            jsonMap.put(key, array);
        }
        refresh();
    }

    @Override
    public String writeToString()
    {
        return writeToString(jsonMap);
    }

    @Override
    public String writeToString(Map<String, Object> objMap)
    {
        String output = null;
        try
        {
            output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objMap);
        }
        catch (IOException e)
        {
            LOG.error("writeToString failed with {} and throw exception {}", textInfo, e.getMessage());
        }
        return output;
    }

    @Override
    public Object convertToObject(Class<?> clazz)
    {
        return convertToObject(textInfo, clazz);
    }

    @Override
    public Object convertToObject(String txtInfo, Class<?> clazz)
    {
        try
        {
            Object obj = mapper.readValue(txtInfo, clazz);
            convertToString(obj);
            return obj;
        }
        catch (IOException e)
        {
            LOG.debug(textInfo);
            LOG.error("cannot convert string to specified Object : " + clazz + " Exception is : " + e.getMessage());
            return null;
        }
    }

    @Override
    public String convertToString(Object obj)
    {
        String txtInfo = "";
        try
        {
            txtInfo = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }
        catch (IOException e)
        {
            LOG.error("cannot convert specified Object to text information(JSON)" + e.getMessage());
        }
        LOG.trace("call convertToString and get : " + txtInfo);
        return txtInfo;
    }

    @Override
    public Object mergeTo(Class<?> clazz, Object targetObj)
    {
        try
        {
            Object srcObj = convertToObject(clazz);
            // use copyTo method to update target object.
            Method mergeFrom = clazz.getDeclaredMethod("mergeFrom", Object.class, Object.class);
            if (targetObj == null)
            {
                // new one if target object doesn't exist.
                targetObj = clazz.newInstance();
            }
            mergeFrom.invoke(targetObj, srcObj, targetObj);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | InstantiationException e)
        {
            LOG.error("something error at mergeFrom method in textInfoClazz" + e.getMessage());
        }
        convertToString(targetObj);
        return targetObj;
    }

}
