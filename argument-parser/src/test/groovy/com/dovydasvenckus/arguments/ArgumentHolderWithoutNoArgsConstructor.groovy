package com.dovydasvenckus.arguments

class ArgumentHolderWithoutNoArgsConstructor {

    @Argument(name = "--first-argument")
    String firstArgumentField

    @Argument(name = "--second-argument")
    String secondArgumentField

    ArgumentHolderWithoutNoArgsConstructor(String firstArgumentField, String secondArgumentField) {
        this.firstArgumentField = firstArgumentField
        this.secondArgumentField = secondArgumentField
    }
}
