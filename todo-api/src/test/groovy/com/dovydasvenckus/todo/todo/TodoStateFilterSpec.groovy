package com.dovydasvenckus.todo.todo

import spock.lang.Specification

class TodoStateFilterSpec extends Specification {

    def 'should return DONE filter when string=true'() {
        expect:
            TodoStateFilter.getStateFilter('true') == TodoStateFilter.DONE
    }

    def 'should return NOT_DONE filter when string=false'() {
        expect:
            TodoStateFilter.getStateFilter('false') == TodoStateFilter.NOT_DONE
    }

    def 'should return NOT_DONE filter when param is malformed'() {
        expect:
            TodoStateFilter.getStateFilter('malformed') == TodoStateFilter.NOT_DONE
    }

    def 'should return ALL filter when passed string is null'() {
        expect:
            TodoStateFilter.getStateFilter(null) == TodoStateFilter.ALL
    }
}
