package com.vergueiro_group.notifyhub_backend_user.controller;

import com.vergueiro_group.notifyhub_backend_user.business.UserService;
import com.vergueiro_group.notifyhub_backend_user.business.dto.EnderecoDTO;
import com.vergueiro_group.notifyhub_backend_user.business.dto.TelefoneDTO;
import com.vergueiro_group.notifyhub_backend_user.business.dto.UserDTO;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
        );
        return jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UserDTO> searchUserForEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.searchUserForEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUserForEmail(@PathVariable String email) {
        userService.deleteUserForEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateDataUser(@RequestBody UserDTO dto,
                                                  @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.updateDataUser(token, dto));
    }

    @PutMapping("/address")
    public ResponseEntity<EnderecoDTO> updateAddress(@RequestBody EnderecoDTO dto,
                                                          @RequestParam("id") String id){
        try {
            UUID uuid = UUID.fromString(id); // Converte String para UUID
            return ResponseEntity.ok(userService.updateAddress(uuid, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Retorna erro se o formato for inválido
        }
    }

    @PutMapping("/telephone")
    public ResponseEntity<TelefoneDTO> updateTelephone(@RequestBody TelefoneDTO dto,
                                                           @RequestParam("id") UUID id){
        return ResponseEntity.ok(userService.updateTelephone(id, dto));
    }

    @PostMapping("/address")
    public ResponseEntity<EnderecoDTO> addAddress(@RequestBody EnderecoDTO dto,
                                                     @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.addAddress(token, dto));
    }

    @PostMapping("/telephone")
    public ResponseEntity<TelefoneDTO> addPhone(@RequestBody TelefoneDTO dto,
                                                  @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.addPhone(token, dto));
    }

}