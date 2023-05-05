package kr.co.khacademy.semi2.filter.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RequestMessageTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("직렬화 성공")
    public void testJsonSerialization(RequestMessage requestMessage, String expected) throws JsonProcessingException {
        assertThat(requestMessage.toJson()).isEqualTo(expected);
    }

    public static Stream<Arguments> testJsonSerialization() {
        return Stream.of(
            arguments(
                RequestMessage.builder()
                    .sender("127.0.0.1")
                    .method("GET")
                    .uri("/")
                    .parameters(Collections.emptyMap())
                    .build(),
                "{\"RequestMessage\":{\"sender\":\"127.0.0.1\",\"method\":\"GET\",\"uri\":\"/\",\"parameters\":{}}}"
            )
        );
    }
}