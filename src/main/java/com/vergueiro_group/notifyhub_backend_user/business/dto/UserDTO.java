package com.vergueiro_group.notifyhub_backend_user.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private List<EnderecoDTO> enderecos;
    private List<TelefoneDTO> telefones;
}
