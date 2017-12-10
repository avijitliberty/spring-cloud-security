package spring.cloud.security.controllers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.unbescape.html.HtmlEscape;

@Controller
public class SecureController {

	@RequestMapping(value = { "/*", "/home" })
	public String getHome() {
		
		return "home";
	}

	@RequestMapping("/hello")
	public String getGreeting(@AuthenticationPrincipal final UserDetails userDetails, Model model) {

		String username = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		for (GrantedAuthority auth : authorities) {
			System.out.println(auth.getAuthority());

		}
		return "hello";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	/** Error page. *//*
	@RequestMapping("/error.html")
	public String error(HttpServletRequest request, Model model) {
		
		model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("<ul>");
		while (throwable != null) {
			errorMessage.append("<li>").append(HtmlEscape.escapeHtml5(throwable.getMessage())).append("</li>");
			throwable = throwable.getCause();
		}
		errorMessage.append("</ul>");
		model.addAttribute("errorMessage", errorMessage.toString());
		return "error";
	}*/
	
	/** Error page. */
    @RequestMapping("/error.html")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
        System.out.println("errorCode: " + request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<ul>");
        while (throwable != null) {
            errorMessage.append("<li>").append(escapeTags(throwable.getMessage())).append("</li>");
            throwable = throwable.getCause();
        }
        errorMessage.append("</ul>");
        model.addAttribute("errorMessage", errorMessage.toString());
        return "error.html";
    }

    /** Substitute 'less than' and 'greater than' symbols by its HTML entities. */
    private String escapeTags(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

	/** Error page. */
	@RequestMapping("/403.html")
	public String forbidden() {
		return "403";
	}

	/**
	 * Return a ModelAndView which will cause the
	 * 'src/main/resources/templates/time.html' template to be rendered, with
	 * the current time.
	 */
	@RequestMapping("/time")
	public ModelAndView time() {
		ModelAndView mav = new ModelAndView("time");
		mav.addObject("currentTime", getCurrentTime());
		return mav;
	}

	private String getCurrentTime() {
		return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
	}
	
	/** Simulation of an exception. */
    @RequestMapping("/simulateError.html")
    public void simulateError() {
        throw new RuntimeException("This is a simulated error message");
    }
}
