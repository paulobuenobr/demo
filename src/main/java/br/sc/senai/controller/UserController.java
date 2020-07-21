package br.sc.senai.controller;

import br.sc.senai.model.User;
import br.sc.senai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController // Isto indica que esta classe é um Controller
//@RequestMapping(path = "/users")
@RequestMapping(path = "/api")
// Isto indica que a URL deve conter o caminho /user após a URL principal da aplicação
public class UserController {

    // Isto significa obter automaticamente o objeto usuárioRepository,
    // que é gerado automaticamente pelo Spring. Vamos usá-lo para manipular os dados (DAO).
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/users") // Endpoint que recebe apenas requisições POST para inclusão de usuários
    public @ResponseBody ResponseEntity<User> addNewUser(@RequestBody User user) {
        try {
            User newUser = userRepository.save(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(path = "/users") // Endpoint que recebe apenas requisições GET para retornar todos os usuários
    public @ResponseBody ResponseEntity<Iterable<User>> getAllUsers() {

        try {
            Iterable<User> users = userRepository.findAll();
            if (((Collection<?>) users).size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}") // Endpoint que recebe apenas requisições DELETE para exclusão de usuários
    public @ResponseBody ResponseEntity<HttpStatus> removeUser(@PathVariable("id") Integer id) {

        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/users/{id}") // Endpoint que recebe apenas requisições PUT para atualização de usuários
    public @ResponseBody ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user) {

        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User updatedUser = userData.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // TODO atualizar
    @PostMapping(path = "/allbyname") // Endpoint que recebe apenas requisições POST para inclusão de usuários
    public @ResponseBody Iterable<User> findByName(@RequestParam String nome) {
        // @ResponseBody significa que a String retornada é a resposta
        // @RequestParam significa que é um parâmetro da solicitação GET ou POST
        return userRepository.findAllByName(nome);
    }
}
