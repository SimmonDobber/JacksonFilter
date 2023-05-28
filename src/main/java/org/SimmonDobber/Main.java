package org.SimmonDobber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.SimmonDobber.serializer.SerializerProvider;

public class Main {

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new SerializerProvider().getSerializer();

            PersonExample personExample1 = new PersonExample("Wojciech", "Baranowski", "21374202137");
            RoleManagerMock.switchRoleToStudent(); //bez uprawnień do peselu
            System.out.println(objectMapper.writeValueAsString(personExample1));

            PersonExample personExample2 = new PersonExample("Kacper", "Donat", "21372137420");
            RoleManagerMock.switchRoleToAdmin(); //z uprawnieniami do peselu
            System.out.println(objectMapper.writeValueAsString(personExample2));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}