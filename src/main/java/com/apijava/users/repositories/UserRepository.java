package com.apijava.users.repositories;

import com.apijava.users.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    /*
    * estender o JPARepository serve muito bem para realizar CRUD's pois já vem com muitos comandos
    * já definidos como findAll,save,findById...
    * mas aqui pode ser implementadas/personalizadas queries com acima uma anotação @Query
    * */
}
