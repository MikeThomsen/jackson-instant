package com.test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class MessageRecordTest {
    @Test
    public void test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());

        String json = "{\n" +
                "\t\"SentDate\": \"2020-12-01T15:40:15\",\n" +
                "\t\"Message\": \"Testing 1, 2, 3, 4\"\n" +
                "}";
        MessageRecord record = mapper.readValue(json, MessageRecord.class);
    }

    public class MultiDateDeserializer extends JsonDeserializer<Instant> {
        private final List<String> formats = Arrays.asList(
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss'Z'"
        );

        @Override
        public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String text = node.textValue();
            Instant found = null;

            for (String format : formats) {
                try {
                    found = new SimpleDateFormat(format).parse(text).toInstant();
                    break;
                } catch (Exception e) {
                    //PASS
                }
            }

            return found;
        }
    }
}
