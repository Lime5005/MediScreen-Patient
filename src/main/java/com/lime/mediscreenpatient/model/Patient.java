package com.lime.mediscreenpatient.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp="^[A-Za-z]*$", message = "First Name has to be text")
    @NotBlank(message = "First Name is mandatory")
    @Size(min = 2, max = 26, message = "First Name length must be 2 to 26 characters")
    private String firstName;


    @Pattern(regexp="^[A-Za-z]*$", message = "Last Name has to be text")
    @NotBlank(message = "Last Name is mandatory")
    @Size(min = 2, max = 26, message = "Last Name length must be 2 to 26 characters")
    private String lastName;

    @Past(message = "Birthdate must be past or present")
    @NotNull(message = "Birthdate is mandatory")
    private Date birthDate;

    @NotNull(message = "Gender is mandatory")
    private Gender sex;
    private String address;
    private String phone;

    public Patient() {
    }

//    public Patient(String firstName, String lastName) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    public Patient(String firstName, String lastName, Date birthDate, Gender sex) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.birthDate = birthDate;
//        this.sex = sex;
//    }

    public Patient(Long id, String firstName, String lastName, Date birthDate, Gender sex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
    }
}
