package org.SimmonDobber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ExcludingObjectMapper();
            PersonExample personExample = new PersonExample("Kacper", "Donat", "21372137420");

            RoleManagerMock.switchRoleToStudent(); //bez uprawnień do peselu
            System.out.println(objectMapper.writeValueAsString(personExample));

            RoleManagerMock.switchRoleToAdmin(); //z uprawnieniami do peselu
            System.out.println(objectMapper.writeValueAsString(personExample));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}