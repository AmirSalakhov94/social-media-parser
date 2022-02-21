package com.mirsalakhov.socialmediaparser.ig.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.mirsalakhov.socialmediaparser.ig.client.IgClientMetaApi;
import com.mirsalakhov.socialmediaparser.ig.dto.ClientProxy;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public record IgClientService(IgClientMetaApi igClientMetaApi) {

    private static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
    private static final int TIMEOUT = 60;

    public Set<IGClient> getFreeClients(int count) {
        Set<IGClient> igClients = new HashSet<>(count);
        igClientMetaApi.getFreeClients(count).forEach(
                clientMetaData -> {
                    ClientProxy bindClientProxy = clientMetaData.getBindClientProxy();
                    OkHttpClient okHttpClient = initOkHttpClient(bindClientProxy.getType(), bindClientProxy.getHostname(),
                            bindClientProxy.getPort(), bindClientProxy.getUsername(), bindClientProxy.getPassword());

                    igClients.add(IGClient.builder()
                            .username(clientMetaData.getUsername())
                            .password(clientMetaData.getPassword())
                            .client(okHttpClient)
                            .build());
                }
        );

        return igClients;
    }

    private OkHttpClient initOkHttpClient(Proxy.Type type, String hostname, int port, String username, String password) {
        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .proxy(new Proxy(type, new InetSocketAddress(hostname, port)))
                .proxyAuthenticator((route, response) -> response.request().newBuilder()
                        .header(PROXY_AUTHORIZATION, Credentials.basic(username, password))
                        .build())
                .build();
    }
}
