package com.dovydasvenckus.todo.util.arguments

import spock.lang.Specification

class ArgumentRetrieverSpec extends Specification {

    SimpleArgumentRetriever testTarget = new SimpleArgumentRetriever()

    String[] args

    def 'should throw exception if argument name is null or empty'() {
        given:
            args = ['test', 'test2']

        when:
            testTarget.getArgument(args, argumentName)

        then:
            thrown(IllegalArgumentException)

        where:
            argumentName << [null, '', '      ']
    }

    def 'should return empty result when args array is empty'() {
        given:
            args = []

        expect:
            !testTarget.getArgument(args,'test').isPresent()
    }

    def 'when argument not found it should return empty result'() {
        given:
            args = ['--db-user', 'test', '--db-pass', 'test2']

        expect:
            !testTarget.getArgument(args, '--db-test').isPresent()
    }

    def 'when argument name is present, but value is not present it should return empty result'() {
        given:
            args = ['--db-user', 'test', '--db-pass']

        expect:
            !testTarget.getArgument(args, '--db-pass').isPresent()
    }

    def 'when argument is found it should return argument value'() {
        given:
            args = args = ['--db-user', 'test', '--db-pass', 'test2']

        expect:
            testTarget.getArgument(args, '--db-pass').get() == 'test2'
    }

}
