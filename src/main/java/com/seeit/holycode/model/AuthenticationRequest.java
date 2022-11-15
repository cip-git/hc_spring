package com.seeit.holycode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor

public class AuthenticationRequest implements Serializable {

    private String username;
    private String password;

}
