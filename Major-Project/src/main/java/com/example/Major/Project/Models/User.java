package com.example.Major.Project.Models;

import com.example.Major.Project.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserId;
    @Column(unique = true, name="Email")
    private String email;
    @Column(name="Password")
    private String password;
    @Column(name="Username")
    private String username;

    @Column(name="date_of_creation")
    //@Temporal(TemporalType.TIMESTAMP)
    private Date createdate;

    @Lob
    @Column(name="profile_pic")
    private byte[] image;

    @Column(name="Role")
    private Role role;

    @Column(name="isDeleted")
    private boolean isDeleted;

   // @OneToMany(mappedBy = "user")
    //private List<Address> address;

    @OneToMany(mappedBy = "user")
    private List<Package> packages;




}
