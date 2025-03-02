package com.vergueiro_group.notifyhub_backend_user.business.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

    private UUID id;
    private String number;
    private String ddd;
}
