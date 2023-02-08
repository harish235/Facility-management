package com.quinbay.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    int id;
    String roleName;

    @JsonIgnore
    @OneToMany(targetEntity = Users.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_fk", referencedColumnName = "id")
    List<Users> issuehistory;
}