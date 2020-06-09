package br.sc.senai.controller;

import br.sc.senai.model.Usuario;
import br.sc.senai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Isto indica que esta classe é um Controller
@RequestMapping(path = "/usuarios")
// Isto indica que a URL deve conter o caminho /usuario após a URL principal da aplicação
public class UsuarioController {

    // Isto significa obter automaticamente o objeto usuárioRepository,
    // que é gerado automaticamente pelo Spring. Vamos usá-lo para manipular os dados (DAO).
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(path = "/add") // Endpoint que recebe apenas requisições POST para inclusão de usuários
    public @ResponseBody
    String addNewUser(@RequestParam String nome
            , @RequestParam String email, @RequestParam String senha) {
        // @ResponseBody significa que a String retornada é a resposta
        // @RequestParam significa que é um parâmetro da solicitação GET ou POST

        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setSenha(senha);
        usuarioRepository.save(u);
        return "Usuário salvo no banco de dados";
    }

    @GetMapping(path = "/all") // Endpoint que recebe apenas requisições GET para retornar todos os usuários
    public @ResponseBody
    Iterable<Usuario> getAllUsers() {
        // Retorna um JSON com todos os usuários
        return usuarioRepository.findAll();
    }
}
