package io.glide.boot.api.dto.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    public CustomLocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        return LocalDateTime.parse(jsonparser.readValueAs(String.class), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
