package com.weimengchao.common.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JacksonTool {

    private static final ObjectMapper objectMapper;

    private JacksonTool() {
    }

    static {
        objectMapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(timeModule);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 将对象转换成json字符串
     *
     * @param data
     * @return
     */
    public static String toJSONString(Object data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T parseObject(String jsonData, Class<T> beanType) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, beanType);
    }

    /**
     * 将 JsonNode 转化为对象
     *
     * @param jsonNode
     * @param beanType
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T parseObject(JsonNode jsonNode, Class<T> beanType) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonNode, beanType);
    }

    /**
     * 将json数据转换对象list
     *
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> List<T> parseList(String jsonData, Class<T> beanType) throws JsonProcessingException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
        return objectMapper.readValue(jsonData, javaType);
    }

    /**
     * String 转为 JsonNode
     *
     * @param content
     * @return
     * @throws JsonProcessingException
     */
    public static JsonNode toJsonNode(String content) throws JsonProcessingException {
        return objectMapper.readTree(content);
    }

    /**
     * 将 JsonNode 数据转换对象list
     *
     * @param jsonNode
     * @param beanType
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> List<T> parseList(JsonNode jsonNode, Class<T> beanType) throws JsonProcessingException {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < jsonNode.size(); i++) {
            result.add(objectMapper.treeToValue(jsonNode.get(i), beanType));
        }
        return result;
    }

    /**
     * 获取 String 类型属性值
     *
     * @param jsonNode
     * @param attributeName
     * @return
     */
    public static String textValue(JsonNode jsonNode, String attributeName) {
        JsonNode node = jsonNode.get(attributeName);
        if (node == null) {
            return null;
        }
        return node.asText();
    }

    /**
     * 获取 Integer 类型属性值
     *
     * @param jsonNode
     * @param attributeName
     * @return
     */
    public static Integer intValue(JsonNode jsonNode, String attributeName) {
        JsonNode node = jsonNode.get(attributeName);
        if (node == null) {
            return null;
        }
        return node.intValue();
    }

    /**
     * 获取 Long 类型属性值
     *
     * @param jsonNode
     * @param attributeName
     * @return
     */
    public static Long longValue(JsonNode jsonNode, String attributeName) {
        JsonNode node = jsonNode.get(attributeName);
        if (node == null) {
            return null;
        }
        return node.longValue();
    }

}
