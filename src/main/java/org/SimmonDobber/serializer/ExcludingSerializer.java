package org.SimmonDobber.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.SimmonDobber.RoleManagerMock;
import org.SimmonDobber.Secured;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ExcludingSerializer extends JsonSerializer<Object> {

    private final JsonSerializer<Object> defaultSerializer;

    public ExcludingSerializer(JsonSerializer<Object> defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Class<?> clazz = object.getClass();
        if (clazz.isAnnotationPresent(Secured.class)) {
            excludeNotPermittedFields(object, clazz);
        }
        this.defaultSerializer.serialize(object, jsonGenerator, serializerProvider);
    }

    private void excludeNotPermittedFields(Object context, Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isFieldNotPermitted)
                .forEach(field -> excludeNotPermittedField(field, context));
    }

    private void excludeNotPermittedField(Field field, Object context) {
        try {
            field.setAccessible(true);
            field.set(context, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isFieldNotPermitted(Field field) {
        String currentRole = RoleManagerMock.getCurrentRole();
        return field.isAnnotationPresent(Secured.class) && !field.getAnnotation(Secured.class).role().equals(currentRole);
    }
}
