package com.dovydasvenckus.todo.utils.common

import spock.lang.Specification

class StringUtilsSpec extends Specification {

    def 'should be empty when null'() {
        expect:
            StringUtils.isNullOrEmpty(null)
    }

    def 'should be empty when empty'() {
        expect:
            StringUtils.isNullOrEmpty('')
    }

    def 'should be empty if white spaces'() {
        expect:
            StringUtils.isNullOrEmpty('     ')
    }

    def 'should not be empty when there is text'() {
        expect:
            !StringUtils.isNullOrEmpty('test')
    }

    def 'should not be empty when there is character between whitespaces'() {
        expect:
            !StringUtils.isNullOrEmpty('     a     ')
    }
}
