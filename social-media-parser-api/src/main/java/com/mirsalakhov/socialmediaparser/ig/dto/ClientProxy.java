package com.mirsalakhov.socialmediaparser.ig.dto;

import lombok.Data;

import java.net.Proxy;

@Data
public class ClientProxy {

    private Proxy.Type type;
    private String hostname;
    private int port;
    private String username;
    private String password;
}
