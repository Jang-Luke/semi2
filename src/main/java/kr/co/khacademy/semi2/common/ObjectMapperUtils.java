package kr.co.khacademy.semi2.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}
