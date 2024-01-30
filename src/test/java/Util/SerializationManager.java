package Util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationManager {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> String serialize(T object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T deserialize(String json, Class<T> clazz) throws Exception {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> T deserializeParamObj(String jsonString, TypeReference<T> typeReference) throws Exception {
        return objectMapper.readValue(jsonString, typeReference);
    }

}
