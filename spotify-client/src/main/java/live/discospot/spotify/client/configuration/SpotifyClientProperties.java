package live.discospot.spotify.client.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Contains properties for Spotify client.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Data
@Validated
@ConfigurationProperties("spotify")
public class SpotifyClientProperties {
    @NotBlank
    private String clientId;
    @NotBlank
    private String clientSecret;
    @NotBlank
    private String redirectUri;
    @NotBlank
    private String scope;
    private boolean showDialog;
}
