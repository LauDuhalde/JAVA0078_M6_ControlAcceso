package cl.web.services;

import java.util.List;

import cl.web.dto.UsuarioDTO;
import cl.web.models.Usuario;

public interface UsuarioService {
    void saveUser(UsuarioDTO usuarioDTO);
    Usuario findByUsername(String username);
    List<UsuarioDTO> findAllUsers();
}
