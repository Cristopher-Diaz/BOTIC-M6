package com.evaluacionFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.evaluacionFinal.dtos.UserDTO;
import com.evaluacionFinal.service.AlumnoService;
import com.evaluacionFinal.service.UserService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String signin(@ModelAttribute UserDTO userDTO, HttpSession session, Model model) {
        try {
            // Generar el token usando el UserService
            String token = userService.signin(userDTO.getUsername(), userDTO.getPassword());

            // Almacenar el token en la sesión
            session.setAttribute("token", token);

            return "redirect:/home";
        } catch (Exception e) {
            // Mostrar un mensaje de error en el login
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // Recuperar el token de la sesión
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }

        // Obtener y mostrar la lista de alumnos
        model.addAttribute("alumnos", alumnoService.findAll());
        return "home";
    }
}
