package com.mirsalakhov.socialmediaparser.ig.dto;

import lombok.Data;

@Data
public class ClientMetaData {

    private String username;
    private String password;
    private ClientProxy bindClientProxy;
}
