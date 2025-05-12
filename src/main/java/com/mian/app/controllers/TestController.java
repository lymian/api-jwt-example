package com.mian.app.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/public")
    public String publico() {
        return "Este endpoint es público.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String soloUser() {
        return "Hola USER. Acceso permitido.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String soloAdmin() {
        return "Hola ADMIN. Acceso permitido.";
    }

    @GetMapping("/any")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String ambos() {
        return "Hola USER o ADMIN. Acceso permitido.";
    }
}
