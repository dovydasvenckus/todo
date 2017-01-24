package com.dovydasvenckus.todo.auth;

import com.dovydasvenckus.todo.helper.auth.UsernamePasswordPair;

import java.util.Base64;

import static com.dovydasvenckus.todo.util.Preconditions.checkArgument;
import static java.util.Arrays.stream;

public class BasicAuthHeaderDecoder {
    private static Base64.Decoder decoder = Base64.getDecoder();

    public static UsernamePasswordPair decode(String authHeader) {
        checkArgument(isStringNonEmpty(authHeader), "Auth header should not be empty or null");
        checkArgument(isValidAuthHeaderStructure(authHeader), "Header should start with 'Basic' keyword");
        authHeader = authHeader.trim();

        String decodedString = new String(decoder.decode(authHeader.split(" ")[1]));
        checkArgument(isValidUsernamePassword(decodedString), "Username and password should be separated by colon");
        return new UsernamePasswordPair(
                decodedString.split(":")[0],
                decodedString.split(":")[1]);
    }

    private static boolean isStringNonEmpty(String string) {
        return string != null && !string.trim().isEmpty();
    }

    private static boolean isValidAuthHeaderStructure(String header) {
        return header.indexOf("Basic ") == 0 && header.split(" ").length == 2;
    }

    private static int countColons(String string) {
        return string.length() - string.replaceAll(":", "").length();
    }

    private static boolean isValidUsernamePassword(String string) {
        if (countColons(string) == 1) {
            String[] usernameAndPass = string.split(":");
            return usernameAndPass.length == 2
                    && stream(usernameAndPass).filter(str -> str.length() > 0).count() == 2;
        }

        return false;
    }
}
