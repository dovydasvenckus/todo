package com.dovydasvenckus.arguments

import spock.lang.Specification

class ArgumentParserSpec extends Specification {

    ArgumentRetriever argumentRetriever = Stub(ArgumentRetriever)

    ArgumentParser<ArgumentHolder> argumentParser = new ArgumentParser<>(ArgumentHolder.class, argumentRetriever)

    ArgumentHolder argumentHolder

    def 'when passing empty arguments array to parser it should not set fields'() {
        given:
            String[] args = []

        and:
            argumentRetriever.getArgument(args, _ as String) >> Optional.empty()

        when:
            argumentHolder = argumentParser.parseParameters(args)

        then:
            argumentHolder.firstArgumentField == null
            argumentHolder.secondArgumentField == null
    }

    def 'should first field, when second field is missing'() {
        given:
            String[] args = ['--first-argument', 'test1']

        and:
            argumentRetriever.getArgument(args, '--first-argument') >> Optional.of('test1')
            argumentRetriever.getArgument(args, '--second-argument') >> Optional.empty()

        when:
            argumentHolder = argumentParser.parseParameters(args)

        then:
            argumentHolder.with {
                firstArgumentField == 'test1'
                secondArgumentField == null
            }
    }

    def 'should set second field, when first field is missing'() {
        given:
            String[] args = ['--second-argument', 'test2']

        and:
            argumentRetriever.getArgument(args, '--first-argument') >> Optional.empty()
            argumentRetriever.getArgument(args, '--second-argument') >> Optional.of('test2')

        when:
            argumentHolder = argumentParser.parseParameters(args)

        then:
            argumentHolder.with {
                firstArgumentField == null
                secondArgumentField == 'test2'
            }
    }

    def 'should set both fields'() {
        given:
            String[] args = ['--first-argument', 'test1', '--second-argument', 'test2']

        and:
            argumentRetriever.getArgument(args, '--first-argument') >> Optional.of('test1')
            argumentRetriever.getArgument(args, '--second-argument') >> Optional.of('test2')

        when:
            argumentHolder = argumentParser.parseParameters(args)

        then:
            argumentHolder.with {
                firstArgumentField == 'test1'
                secondArgumentField == 'test2'
            }
    }

    def 'should throw exception when no args constructor is missing'() {
        given:
            ArgumentParser<ArgumentHolderWithoutNoArgsConstructor> argumentParser = new ArgumentParser<>(
                    ArgumentHolderWithoutNoArgsConstructor,
                    argumentRetriever

            )

            String[] args = ['--first-argument', 'test1', '--second-argument', 'test2']

        when:
            argumentHolder = argumentParser.parseParameters(args)

        then:
            ArgumentParseException exception = thrown(ArgumentParseException)
            exception.message == 'Error while trying to initialize arguments class. Arguments class requires accessible no args constructor'
    }

}
