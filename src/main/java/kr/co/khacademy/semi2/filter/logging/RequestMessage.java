package kr.co.khacademy.semi2.filter.logging;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.khacademy.semi2.common.ObjectMapperUtils;
import lombok.Builder;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.WRAPPER_OBJECT
)
public class RequestMessage {

    String sender;
    String method;
    String uri;
    Map<String, List<String>> parameters;

    @Builder
    private static RequestMessage of(
        String sender,
        String method,
        String uri,
        Map<String, String[]> parameters
    ) {
        Map<String, List<String>> newParameters = parameters.entrySet()
            .stream()
            .map(entry -> Map.entry(entry.getKey(), Arrays.asList(entry.getValue())))
            .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

        return new RequestMessage(sender, method, uri, newParameters);
    }

    public static RequestMessage of(HttpServletRequest httpServletRequest) {
        String sender = httpServletRequest.getLocalAddr();
        String method = httpServletRequest.getMethod();
        String uri = httpServletRequest.getRequestURI();
        Map<String, String[]> parameters = httpServletRequest.getParameterMap();

        return RequestMessage.of(
            sender,
            method,
            uri,
            parameters
        );
    }

    public String toJson() throws JsonProcessingException {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
