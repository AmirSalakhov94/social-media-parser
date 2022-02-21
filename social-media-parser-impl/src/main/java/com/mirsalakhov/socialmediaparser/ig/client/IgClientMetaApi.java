package com.mirsalakhov.socialmediaparser.ig.client;

import com.mirsalakhov.socialmediaparser.ig.dto.ClientMetaData;

import java.util.Set;

public interface IgClientMetaApi {

    Set<ClientMetaData> getFreeClients(int count);
}
