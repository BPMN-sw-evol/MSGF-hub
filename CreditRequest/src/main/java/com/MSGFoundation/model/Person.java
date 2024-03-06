package com.MSGFoundation.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "PERSON")
@Data
public class Person {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthDate")
    private LocalDate birthDate;
    @Column(name = "phoneNumber")
    private String phoneNumber;
}
