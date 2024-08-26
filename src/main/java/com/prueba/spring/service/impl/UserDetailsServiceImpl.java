package com.prueba.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.prueba.spring.controller.dto.AuthRequestDTO;
import com.prueba.spring.controller.dto.AuthResponseDTO;
import com.prueba.spring.entity.Usuario;
import com.prueba.spring.repository.UsuarioRepository;
import com.prueba.spring.util.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new UsernameNotFoundException("El usuario no esta disponible con el email " + email);
        }

        Usuario usuario = usuarioOpt.get();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_".concat(usuario.getRol().getRol().name())));

        return new User(usuario.getEmail(), usuario.getPassword(), authorities);
    }

    public AuthResponseDTO loginUser(AuthRequestDTO authRequestDTO) {
        String email = authRequestDTO.email();
        String password = authRequestDTO.password();
        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(email, "Usuario logeado correctamente", accessToken,
                true);
        return authResponseDTO;
    }

    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("El email es invalido");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("El password es invalido");
        }

        return new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
