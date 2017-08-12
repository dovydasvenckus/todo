package com.dovydasvenckus.todo.util.arguments;

import java.util.Optional;

interface ArgumentRetriever {
    Optional<String> getArgument(String[] args, String argumentName);
}
