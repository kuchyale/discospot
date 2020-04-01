package live.discospot.web.controller;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.PagingCursorbased;
import com.wrapper.spotify.model_objects.specification.PlayHistory;
import com.wrapper.spotify.model_objects.specification.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

/**
 * Controller for dashboard page.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@Controller
public class DashboardController {
    private final OAuth2AuthorizedClientService authorizedClientService;

    /**
     * New instance.
     *
     * @param authorizedClientService {@link OAuth2AuthorizedClientService}
     */
    public DashboardController(OAuth2AuthorizedClientService authorizedClientService) {
        Assert.notNull(authorizedClientService, "authorizedClientService must not be null");

        this.authorizedClientService = authorizedClientService;
    }

    /**
     * Return dashboard page.
     *
     * @param authToken {@link OAuth2AuthenticationToken}
     * @return dashboard page with model data
     */
    @RequestMapping("/dashboard")
    public ModelAndView authorize(OAuth2AuthenticationToken authToken) {
        Assert.notNull(authToken, "authToken must not be null");

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                authToken.getAuthorizedClientRegistrationId(), authToken.getName());

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(authorizedClient.getAccessToken().getTokenValue())
                .setRefreshToken(authorizedClient.getRefreshToken().getTokenValue())
                .build();

        User user;
        PagingCursorbased<PlayHistory> playHistories;
        try {
            user = spotifyApi.getCurrentUsersProfile().build().execute();
            playHistories = spotifyApi.getCurrentUsersRecentlyPlayedTracks().build().execute();
        } catch (IOException | SpotifyWebApiException e) {
            throw new IllegalStateException("Error was occurred when calling Spotify API. Error: " + e.getMessage(), e);
        }

        return new ModelAndView("dashboard", Map.of("user", user,
                "playHistories", playHistories.getItems()));
    }
}
