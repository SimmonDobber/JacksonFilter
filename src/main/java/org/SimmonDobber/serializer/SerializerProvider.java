package org.SimmonDobber.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class SerializerProvider {

    private final ObjectMapper objectMapper;

    public SerializerProvider() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(getCustomModule());
    }

    private SimpleModule getCustomModule() {
        return new SimpleModule() {
            @Override
            public void setupModule(SetupContext context) {
                super.setupModule(context);
                context.addBeanSerializerModifier(getCustomBeanSerializerModifier());
            }
        };
    }

    private BeanSerializerModifier getCustomBeanSerializerModifier() {
        return new BeanSerializerModifier() {
            @Override
            public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription desc, JsonSerializer<?> serializer) {
                return new ExcludingSerializer((JsonSerializer<Object>) serializer);
            }
        };
    }

    public ObjectMapper getSerializer() {
        return this.objectMapper;
    }
}
