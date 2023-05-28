package org.SimmonDobber;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonFilter("security")
public class PersonExample {

    private String firstName;
    private String lastName;
    @Secured(role = "admin")
    private String pesel;
}
