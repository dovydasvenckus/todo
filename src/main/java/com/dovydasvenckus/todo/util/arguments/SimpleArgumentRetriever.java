package com.dovydasvenckus.todo.util.arguments;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import static com.dovydasvenckus.todo.util.common.Preconditions.checkArgument;
import static com.dovydasvenckus.todo.util.common.StringUtils.isNullOrEmpty;
import static java.util.Optional.empty;
import static java.util.Optional.of;

class SimpleArgumentRetriever implements ArgumentRetriever{

    public Optional<String> getArgument(String[] args, String argumentName) {
        checkArgument(!isNullOrEmpty(argumentName), "Argument name should not be null");

        Iterator<String> argumentIterator = Arrays.asList(args).iterator();

        while (argumentIterator.hasNext()) {
            String currentValue = argumentIterator.next().trim();

            if (currentValue.equals(argumentName)) {
                break;
            }
        }

        return argumentIterator.hasNext() ? of(argumentIterator.next()) : empty();
    }
}
