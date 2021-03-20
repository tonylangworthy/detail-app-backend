package com.webbdealer.detailing.security;

public class SecurityConstants {
    public static final String SECRET = "cFrCxgRsDk8Md7uNtMcU4iJ0pge4uHNcaDPZnlLQw3i5YJzY3L9Of5loCmuCbu9Dl-1raKXumgO0CW0S_-vMxeH_HpDNkjZs3uYsz9w1ybN83X9iZBkuiKrbWIzV4k9u";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
//    public static final long EXPIRATION_TIME = 60_000; // 1 minute
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/json";
    public static final String SIGN_UP_URL = "/register";
}
