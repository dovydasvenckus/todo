package com.dovydasvenckus.common

import spock.lang.Specification

class PreconditionsSpec extends Specification {

    def 'when precondition correct should not throw exception'() {
        when:
            com.dovydasvenckus.common.Preconditions.checkArgument(true)

        then:
            noExceptionThrown()
    }

    def 'precondition with message should not throw exception it is correct'() {
        when:
            com.dovydasvenckus.common.Preconditions.checkArgument(true, 'Error message')

        then:
            noExceptionThrown()
    }

    def 'should throw exception, when precondition is incorrect'() {
        when:
            com.dovydasvenckus.common.Preconditions.checkArgument(false)

        then:
            thrown(IllegalArgumentException)
    }

    def 'should throw exception with message, when precondition is incorrect'() {
        when:
            com.dovydasvenckus.common.Preconditions.checkArgument(false, 'Error message')

        then:
            def exception = thrown(IllegalArgumentException)
            exception.message == 'Error message'
    }

}
