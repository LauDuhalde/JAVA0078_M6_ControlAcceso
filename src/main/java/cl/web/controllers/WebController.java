package cl.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cl.web.dto.UsuarioDTO;
import cl.web.models.Usuario;
import cl.web.services.UsuarioServiceImpl;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

	@Autowired
	UsuarioServiceImpl usuarioServiceImpl;

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String ingreso() {
		return "perfil/panel";
	}
	
	@GetMapping("/panel")
	public String panel() {
		return "perfil/panel";
	}
	
	@GetMapping("/perfil")
	public String perfil(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName(); // devuelve el username logueado
	    
	    model.addAttribute("usuario", usuarioServiceImpl.findByUsername(username));
		return "perfil/perfil";
	}
	
//	@GetMapping("/admin")
//	public String detalleAdmin(Model model) {
//		model.addAttribute("usuarios", usuarioServiceImpl.findAllUsers());
//		return "admin/admin";
//	}
    @GetMapping("/admin")
    public String detalleAdmin(Model model) {
    // Obtener todos los usuarios como entidades Usuario
    List<Usuario> usuarios = usuarioServiceImpl.findAllUsers()
            .stream()
            .map(dto -> usuarioServiceImpl.findByUsername(dto.getUsername()))
            .collect(Collectors.toList());

    model.addAttribute("usuarios", usuarios);
    return "admin/admin";
}
	
	@GetMapping("/registro")
    public String mostrarRegistroForm(Model model) {
		UsuarioDTO user = new UsuarioDTO();
        model.addAttribute("usuario", user);
        return "register";
    }
	
    @PostMapping("/registro")
    public String registro_guardar(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioDto,
                                   BindingResult result,
                                   Model model) {

        Usuario existeUsername = usuarioServiceImpl.findByUsername(usuarioDto.getUsername());
        if (existeUsername != null) {
            result.rejectValue("username", null, "Nombre de usuario ya está en uso");
        }

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuarioDto);
            return "register";
        }

        usuarioServiceImpl.saveUser(usuarioDto);
        return "redirect:/login";
    }
}
