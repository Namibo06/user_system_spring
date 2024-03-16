package com.apijava.users.controllers;

import com.apijava.users.dtos.UserRecordDto;
import com.apijava.users.dtos.UserUpdateRecordDto;
import com.apijava.users.models.UserModel;
import com.apijava.users.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@CrossOrigin(origins = "*",methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
public class UserController {

    /*
    * Autowired serve para injetar automaticamente dependências em classes gerenciadas pelo Spring
    * */
    @Autowired
    UserRepository userRepository;

    /*PostMapping é para o Spring saber quese trata de uma requisição Post*/
    @PostMapping("/users")
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRecordDto userRecordDto){
        /*
        * ResponseEntity é o retorno para o banco
        * @RequestBody são os campos que serão validados e salvos
        * @Valid é para validar todas as validações que estão no dto
        * */

        //guardando a instância da classe UserModel numa variável
        var userModel = new UserModel();

        //copia as propriedades do primeiro objeto e passa para o segundo
        //isso acontece porquê dto não pode ser guardado no banco,mas deve ser sim uma entidade(model)
        BeanUtils.copyProperties(userRecordDto,userModel);

        //retorno uma resposta http com status 201 criando uma instância da classe ResponseEntity e no corpo da requisição vai ser exibido o que foi salvo
        //aqui utilizo um método já definido do JPARepository que estendi e salvei a model no banco de dados,representando um usuário
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        /*
         *aqui digo que o retorno será uma List(Lista que costuma ser muito mais usado que Array) de UserModel(retorna uma entidade de usuários)
         * */
        //aqui armazeno numa variável onde o retorno é uma lista de usuários
        //pego o método findAll que é o equivalente ao all() do laravel,pela userRepository que estendo do JPARepository
        List<UserModel> usersList = userRepository.findAll();

        /*
        * se a lista de usuário não for vazia,percorro todos e pego o id para manipular o link e retorno os usuários da lista
        * */
        if (!usersList.isEmpty()) {
            for (UserModel user : usersList) {
                UUID id = user.getId();
                user.add(linkTo(methodOn(UserController.class).getOneUser(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id){
        /*
        * @PathVariable serve de mapeamento para uma rota específica da Api,onde o valor do parametro é o id,e depois é dito o seu tipo
        * */

        //Optional representa um valor que pode estar presente ou ausente,ele é usado para evitar verificações desnecessárias de nulos
        Optional<UserModel> user = userRepository.findById(id);

        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        user.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserUpdateRecordDto userUpdateRecordDto){
        Optional<UserModel> user = userRepository.findById(id);

        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var userModel = user.get();
        /*alternativa ao patch*/
        userModel.setFirst_name(userModel.getFirst_name());
        userModel.setLast_name(userModel.getLast_name());
        userModel.setEmail(userModel.getEmail());
        userModel.setNivel_user(userModel.getNivel_user());

        BeanUtils.copyProperties(userUpdateRecordDto,userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id){
        Optional<UserModel> user = userRepository.findById(id);

        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        userRepository.delete(user.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado");
    }
}
