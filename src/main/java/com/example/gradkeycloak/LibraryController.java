package com.example.gradkeycloak;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LibraryController {

	private final HttpServletRequest request;

	@Autowired
	public LibraryController(HttpServletRequest request) {
		this.request = request;
	}

	private KeycloakSecurityContext getKeycloakSecurityContext(){
		return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
	}

	private void configCommonAttributes(Model model){
		model.addAttribute("name", getKeycloakSecurityContext().getIdToken().getGivenName());
	}

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/")
	public String getHome(){
		return "index";
	}

	@GetMapping("/books")
	public String getBooks(Model model){
		configCommonAttributes(model);
		System.out.println("Id token" + getKeycloakSecurityContext().getIdTokenString());
		model.addAttribute("books", bookRepository.readAll());
		return "books";
	}

	@GetMapping("/manager")
	public String getManager(Model model){
		configCommonAttributes(model);
		model.addAttribute("books", bookRepository.readAll());
		return "manager";
	}

	@GetMapping("/logout")
	public String logout() throws ServletException {
		request.logout();
		return "redirect:/";
	}
}
