package service;

public final class PasswordPolicy {

    private PasswordPolicy() {}

    public static boolean isValid(String password) {
        if (password == null) return false;
        if (password.length() < 6) return false;

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        return hasLetter && hasDigit && hasSpecial;
    }

    public static String requirementText() {
        return "Password must include: letter, number, special character (min 6 chars).";
    }
}
