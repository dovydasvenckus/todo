package helper.auth

import com.dovydasvenckus.todo.auth.AuthService
import spark.Request
import spock.lang.Specification

class AuthorizationServiceSpec extends Specification {

    def 'should authorize user with correct credentials'() {
        given:
            def authService = new AuthService('myusername', 'mypassword')
            def request = getMockRequest()
        when:
            def authorized = authService.isAuthorized(request)
        then:
            authorized
    }

    def 'should authorize when username has letters in wrong case'() {
        given:
            def authService = new AuthService('MyUsername', 'mypassword')
            def request = getMockRequest()
        when:
            def authorized = authService.isAuthorized(request)
        then:
            authorized
    }

    def 'password should be case sensitive'() {
        given:
        def authService = new AuthService('myusername', 'Mypassword')
            def request = getMockRequest()
        when:
            def authorized = authService.isAuthorized(request)
        then:
            !authorized
    }

    def 'should not let create auth service without username'() {
        when:
            new AuthService('', 'pass')
        then:
            thrown IllegalArgumentException
    }

    def 'should not let create auth service without password'() {
        when:
            new AuthService('username', null)
        then:
            thrown IllegalArgumentException
    }

    private Request getMockRequest() {
        def request = Mock(Request)
        request.headers('Authorization') >> 'Basic bXl1c2VybmFtZTpteXBhc3N3b3Jk'
        return request
    }
}
