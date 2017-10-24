package com.vilen.realworld;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by vilen on 17/10/22.
 */
@Configuration
public class JacksonCustomizations {

    @Bean
    public Module realWordModules() {
        return new RealworldModules();
    }


    private static class RealworldModules extends SimpleModule {
        public RealworldModules() {
            addSerializer(DateTime.class, new DateTimeSerializer());
        }
    }
    private static class DateTimeSerializer extends StdSerializer<DateTime> {
        protected DateTimeSerializer() {
            super(DateTime.class);
        }

        @Override
        public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (dateTime == null) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeString(ISODateTimeFormat.dateTime().withZoneUTC().print(dateTime));
            }
        }
    }
}
