package org.SimmonDobber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ExcludingObjectMapper extends ObjectMapper {

    private final SimpleFilterProvider filterProvider;

    public ExcludingObjectMapper() {
        this.filterProvider = new SimpleFilterProvider();
    }

    @Override
    public String writeValueAsString(Object value) throws JsonProcessingException {
        excludeNotPermittedFields(value.getClass());
        return super.writeValueAsString(value);
    }

    private void excludeNotPermittedFields(Class<?> clazz) {
        Set<String> notPermittedFields = getNotPermittedFields(clazz);
        this.filterProvider.addFilter("security", SimpleBeanPropertyFilter.filterOutAllExcept(notPermittedFields));
        setFilterProvider(this.filterProvider);
    }

    private Set<String> getNotPermittedFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isFieldPermitted)
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private boolean isFieldPermitted(Field field) {
        String currentRole = RoleManagerMock.getCurrentRole();
        return !field.isAnnotationPresent(Secured.class) || field.getAnnotation(Secured.class).role().equals(currentRole);
    }
}
