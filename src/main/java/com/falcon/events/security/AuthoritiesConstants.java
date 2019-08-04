package com.falcon.events.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String ORGANIZER = "ROLE_ORGANIZER";

    public static final String MEMBER = "ROLE_MEMBER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
