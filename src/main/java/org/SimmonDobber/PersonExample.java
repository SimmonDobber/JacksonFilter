package org.SimmonDobber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Secured
public class PersonExample {

    private String firstName;
    private String lastName;
    @Secured(role = "admin")
    private String pesel;
}
