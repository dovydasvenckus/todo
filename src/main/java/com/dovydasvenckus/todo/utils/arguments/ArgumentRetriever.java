package com.dovydasvenckus.todo.utils.arguments;

import java.util.Optional;

interface ArgumentRetriever {
    Optional<String> getArgument(String[] args, String argumentName);
}