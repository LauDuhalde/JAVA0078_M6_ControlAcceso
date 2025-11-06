package cl.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import cl.web.services.UsuarioServiceImpl;

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
	
	@GetMapping("/admin")
	public String detalleAdmin(Model model) {
		model.addAttribute("usuarios", usuarioServiceImpl.findAllUsers());
		return "admin/admin";
	}
}
