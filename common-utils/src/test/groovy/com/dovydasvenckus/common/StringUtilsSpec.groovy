package com.dovydasvenckus.common

import spock.lang.Specification

class StringUtilsSpec extends Specification {

    def 'should be empty when null'() {
        expect:
            com.dovydasvenckus.common.StringUtils.isNullOrEmpty(null)
    }

    def 'should be empty when empty'() {
        expect:
            com.dovydasvenckus.common.StringUtils.isNullOrEmpty('')
    }

    def 'should be empty if white spaces'() {
        expect:
            com.dovydasvenckus.common.StringUtils.isNullOrEmpty('     ')
    }

    def 'should not be empty when there is text'() {
        expect:
            !com.dovydasvenckus.common.StringUtils.isNullOrEmpty('test')
    }

    def 'should not be empty when there is character between whitespaces'() {
        expect:
            !com.dovydasvenckus.common.StringUtils.isNullOrEmpty('     a     ')
    }
}
