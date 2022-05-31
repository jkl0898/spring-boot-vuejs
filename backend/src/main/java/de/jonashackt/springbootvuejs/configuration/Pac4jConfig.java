package de.jonashackt.springbootvuejs.configuration;


import org.pac4j.core.client.Clients;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.config.Config;
import org.pac4j.core.http.callback.NoParameterCallbackUrlResolver;
import org.pac4j.oidc.client.AzureAdClient;
import org.pac4j.oidc.config.AzureAdOidcConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pac4jConfig {

    @Bean
    public Config config() {
        System.out.println("## Hi Pac4j");
        String client_id = "1c99b8d9-140d-489d-a5a9-0cea31f50ffd";
        String client_secret = "wUp8Q~aATSA4KrdH_77C89kVaIoWesj9M0JsZat7";
        String tenant_id = "1b5ed31f-7d74-4fce-a3c2-3e9a32065293";
        String discoveryURI = "https://login.microsoftonline.com/38c46e5a-21f0-46e5-940d-3ca06fd1a330/.well-known/openid-configuration";
        String preferredJwsAlgorithm = "RS256";
        //String callbackUrl = "http://localhost:8098/";
        //String callbackUrl = "https://localhost:8443/";
        String callbackUrl = "https://localhost:8443/bootstrap";

        final AzureAdOidcConfiguration configuration = new AzureAdOidcConfiguration();
        configuration.setClientId(client_id);
        configuration.setSecret(client_secret);
        configuration.setTenant(tenant_id);
        configuration.setPreferredJwsAlgorithm(preferredJwsAlgorithm);
        configuration.setDiscoveryURI(discoveryURI);
        final IndirectClient azureAdClient = new AzureAdClient(configuration);
        azureAdClient.setCallbackUrlResolver(new NoParameterCallbackUrlResolver());
        azureAdClient.setCallbackUrl(callbackUrl);


        //final Clients clients = new Clients("https://localhost:8443/", azureAdClient);
        final Clients clients = new Clients(azureAdClient);
        return new Config(clients);
    }
}
