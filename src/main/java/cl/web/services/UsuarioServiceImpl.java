package cl.web.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.web.dto.UsuarioDTO;
import cl.web.models.Usuario;
import cl.web.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

	private final PasswordEncoder passwordEncoder;

	public UsuarioServiceImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder= passwordEncoder;
	}
	
    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("No encontrado"));

        return User.builder()
            .username(usuario.getUsername())
            .password(usuario.getPassword())
            .roles(usuario.getRole())
            .build();
    }

	@Override
	public void saveUser(UsuarioDTO usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setRole(usuarioDto.getRole());
        // encriptar password usando spring security bcrypt
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        
        usuarioRepo.save(usuario);
		
	}

	@Override
	public List<UsuarioDTO> findAllUsers() {
		 List<Usuario> usuario = usuarioRepo.findAll();
		 
		 return usuario.stream()
	                .map(this::mapToUserDto)
	                .collect(Collectors.toList());
	}
	
   private UsuarioDTO mapToUserDto(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setRole(usuario.getRole());
        return usuarioDTO;
   }

   @Override
   public Usuario findByUsername(String username) {
	return usuarioRepo.findByUsername(username).orElse(null);
   }
}
