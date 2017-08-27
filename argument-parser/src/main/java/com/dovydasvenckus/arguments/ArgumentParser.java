package com.dovydasvenckus.arguments;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArgumentParser<T> {

    private final Class<T> argumentsClass;

    private final ArgumentRetriever argumentRetriever;

    public ArgumentParser(Class<T> argumentsClass) {
        this.argumentsClass = argumentsClass;
        this.argumentRetriever = new SimpleArgumentRetriever();
    }

    ArgumentParser(Class<T> argumentsClass, ArgumentRetriever argumentRetriever) {
        this.argumentsClass = argumentsClass;
        this.argumentRetriever = argumentRetriever;

    }

    public T parseParameters(String[] args) {
        List<Field> annotatedFields = Stream.of(argumentsClass.getDeclaredFields())
                .filter(isFieldAnnotated())
                .collect(Collectors.toList());

        T instanceOfArgumentsClass = initializeInstanceOfArgumentClass();

        annotatedFields.forEach(field -> setFieldValue(args, instanceOfArgumentsClass, field));

        return instanceOfArgumentsClass;
    }

    private void setFieldValue(String[] args, Object instanceOfArgumentsClass, Field field) {
        try {
            field.setAccessible(true);

            Optional<String> currentArgument = argumentRetriever.getArgument(args, field.getDeclaredAnnotation(Argument.class).name());

            if (currentArgument.isPresent()) {
                field.set(instanceOfArgumentsClass, currentArgument.get());
            }
        } catch (IllegalAccessException ex) {
            throw new ArgumentParseException("Error while setting argument class instance field", ex);
        }
    }

    private Predicate<Field> isFieldAnnotated() {
        return field -> field.getDeclaredAnnotationsByType(Argument.class).length > 0;
    }

    private T initializeInstanceOfArgumentClass() {
        try {
            return argumentsClass.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new ArgumentParseException(
                    "Error while trying to initialize arguments class. " +
                            "Arguments class requires accessible no args constructor", ex);
        }
    }
}
