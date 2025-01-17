package com.evaluacionFinal.service;

import com.evaluacionFinal.JwtTokenProvider;
import com.evaluacionFinal.RestServiceException;
import com.evaluacionFinal.model.User;
import com.evaluacionFinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final ObjectFactory<AuthenticationManager> authenticationManagerFactory;

    @Autowired
    public UserService(ObjectFactory<AuthenticationManager> authenticationManagerFactory) {
        this.authenticationManagerFactory = authenticationManagerFactory;
    }

    // Método para autenticar un usuario y devolver un token JWT
    public String signin(String username, String password) {
        try {
            AuthenticationManager authenticationManager = authenticationManagerFactory.getObject();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new RestServiceException("Usuario no encontrado", HttpStatus.UNAUTHORIZED);
            }
            return jwtTokenProvider.createToken(username, user.getRoles());
        } catch (Exception e) {
            throw new RestServiceException("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }
    }
    
    public User signup(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RestServiceException("El nombre de usuario ya existe", HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RestServiceException("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .build();
    }
}
