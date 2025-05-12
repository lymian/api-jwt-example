package com.mian.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.mian.app.dtos.AuthResponseDTO;
import com.mian.app.dtos.RegisterDTO;
import com.mian.app.entities.Rol;
import com.mian.app.entities.Usuario;
import com.mian.app.entities.enums.RolEnum;
import com.mian.app.repositories.IRolRepository;
import com.mian.app.repositories.IUsuarioRepository;
import com.mian.app.security.JwtService;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRolRepository rolRepository;

    public AuthResponseDTO login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .toList();

        return new AuthResponseDTO(token, userDetails.getUsername(), roles);
    }

    public void register(RegisterDTO request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));

        // Asignar rol por defecto, por ejemplo VENDEDOR
        Rol rolPorDefecto = rolRepository.findByNombre(RolEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
        nuevoUsuario.setRol(rolPorDefecto);

        usuarioRepository.save(nuevoUsuario);
    }
}