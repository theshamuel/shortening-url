package com.theshamuel.shrturl.redirect;

/**
 * The interface Redirect service.
 */
public interface RedirectService {
    /**
     * Gets redirect url.
     *
     * @param shortUrl   the short url
     * @param userAgent  the user agent
     * @param remoteHost the remote host
     * @return the redirect url
     */
    String getRedirectUrl(String shortUrl, String userAgent, String remoteHost);
}
