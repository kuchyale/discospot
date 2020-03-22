package live.discospot.spotify.client.configuration;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Spotify client.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(SpotifyClientProperties.class)
public class SpotifyClientConfiguration {

    @Bean
    public SpotifyApi spotifyApi(SpotifyClientProperties clientProperties) {
        return new SpotifyApi.Builder()
                .setClientId(clientProperties.getClientId())
                .setClientSecret(clientProperties.getClientSecret())
                .setRedirectUri(SpotifyHttpManager.makeUri(clientProperties.getRedirectUri()))
                .build();
    }

    @Bean
    public AuthorizationCodeUriRequest authorizationCodeUriRequest(SpotifyApi spotifyApi,
                                                                   SpotifyClientProperties clientProperties) {
        return spotifyApi.authorizationCodeUri()
                .scope(clientProperties.getScope())
                .show_dialog(clientProperties.isShowDialog())
                .build();
    }
}
