package com.apijava.users.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

/*
*estender RepresentationModel é utilizar o pacote HATEOAS do Spring que nada mais  é que,
*pode utilizar hipermidias,fazer manipulações com links
* */
@Entity
@Table(name = "tb_users")
public class UserModel extends RepresentationModel<UserModel> implements Serializable {
    //número para identificar a versão da classe que foi usada no processo de serialização
    private static final long serialVersionUID=1L;

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String first_name;
    private String last_name;
    private String email;
    private String nivel_user;
    private String password;


    /*getters e setters*/
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNivel_user() {
        return nivel_user;
    }

    public void setNivel_user(String nivel_user) {
        this.nivel_user = nivel_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
