package spring.cloud.security.controllers;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecureController {

	@RequestMapping(value={"/*", "/home"})
	public String getHome() {
		return "home";
	}
	
	@RequestMapping("/hello")
	public String getGreeting(@AuthenticationPrincipal final UserDetails userDetails,
			                  Model model) {
		
		String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for(GrantedAuthority auth : authorities) {
        	System.out.println(auth.getAuthority());
        	
        }     
		return "hello";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
