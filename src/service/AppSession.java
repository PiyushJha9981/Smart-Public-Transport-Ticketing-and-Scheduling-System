package service;

public final class AppSession {

    private static String currentUsername;

    private AppSession() {}

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void login(String username) {
        currentUsername = username;
    }

    public static void logout() {
        currentUsername = null;
    }
}
