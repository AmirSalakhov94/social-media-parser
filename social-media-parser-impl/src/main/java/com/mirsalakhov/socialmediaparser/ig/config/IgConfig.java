package com.mirsalakhov.socialmediaparser.ig.config;

import com.github.instagram4j.instagram4j.IGClient;
import com.mirsalakhov.socialmediaparser.ig.dto.ClientProxy;
import lombok.RequiredArgsConstructor;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mirsalakhov.socialmediaparser.ig.client.IgClientMetaApi;
import com.mirsalakhov.socialmediaparser.ig.provider.IgClientProvider;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class IgConfig {

    private static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
    private static final int CLIENT_COUNT = 1;
    private static final int TIMEOUT = 60;

    private final IgClientMetaApi igClientMetaApi;

    @Bean
    public IgClientProvider igClientProvider(Set<IGClient> igClients) {
        IgClientProvider igClientProvider = new IgClientProvider();
        igClients.forEach(igClientProvider::addClient);
        return igClientProvider;
    }

    @Bean
    public Set<IGClient> igClients() {
        Set<IGClient> igClients = new HashSet<>(CLIENT_COUNT);
        igClientMetaApi.getListClientMetaData(CLIENT_COUNT).forEach(
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
