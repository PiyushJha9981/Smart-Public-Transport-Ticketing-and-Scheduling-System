package service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class UserService {

    // Simple in-memory user store (username -> password)
    private static final Map<String, String> USERS = Collections.synchronizedMap(new HashMap<>());

    private UserService() {}

    static {
        // Dummy existing user
        USERS.put("demo", "Demo@123");
    }

    public static boolean userExists(String username) {
        return username != null && USERS.containsKey(username.trim());
    }

    public static void signUp(String username, String password) {
        String u = normalizeUsername(username);
        if (u.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (USERS.containsKey(u)) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (!PasswordPolicy.isValid(password)) {
            throw new IllegalArgumentException(PasswordPolicy.requirementText());
        }
        USERS.put(u, password);
    }

    public static void authenticate(String username, String password) {
        String u = normalizeUsername(username);
        if (u.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (!USERS.containsKey(u)) {
            throw new IllegalArgumentException("User not found. Please sign up.");
        }
        String expected = USERS.get(u);
        if (expected == null || !expected.equals(password)) {
            throw new IllegalArgumentException("Invalid password.");
        }
    }

    private static String normalizeUsername(String username) {
        return username == null ? "" : username.trim();
    }
}
