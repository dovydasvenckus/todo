package helper.auth

import com.dovydasvenckus.todo.helper.auth.UsernamePasswordPair
import spock.lang.Specification

import static com.dovydasvenckus.todo.auth.BasicAuthHeaderDecoder.decode

class BasicAuthHeaderDecoderSpec extends Specification {

    def "empty header should cause invalid argument exception"() {
        when:
            decode(emptyHeader)
        then:
            IllegalArgumentException ex = thrown()
            ex.message == 'Auth header should not be empty or null'
        where:
            emptyHeader << [null, ' ', '   ', " ", '             ']
    }

    def 'header without Basic keyword should throw invalid argument exception'() {
        when:
            decode(wrongHeader)
        then:
            IllegalArgumentException ex = thrown()
            ex.message == "Header should start with 'Basic' keyword"
        where:
            wrongHeader << ['bXl1c2VybmFtZTp0ZXN0', 'test', '   bXl1c2VybmFtZTp0ZXN0     ']
    }

    def "should not decode header with misused 'Basic' keyword"() {
        when:
            decode(wrongHeader)
        then:
            IllegalArgumentException ex = thrown()
            ex.message == "Header should start with 'Basic' keyword"
        where:
            wrongHeader << ['Basic ', 'Basic : bXl1c2VybmFtZTp0ZXN0', 'bXl1c2VybmFtZTp0ZXN0 Basic']
    }

    def 'should decode correct header'() {
        when:
            UsernamePasswordPair usernamePasswordPair = decode('Basic bXl1c2VybmFtZTp0ZXN0')
        then:
            usernamePasswordPair.username == 'myusername'
            usernamePasswordPair.password == 'test'
    }

    def 'should not parse not valid base64 strings'() {
        when:
            decode('Basic test:test')
        then:
            thrown IllegalArgumentException
    }

    def 'should fail with wrong amount of colons in decoded code'() {
        given:
            String authHeader = 'Basic ' + new String(Base64.getEncoder().encode(usernamePassword.bytes))
        when:
            decode(authHeader)
        then:
            IllegalArgumentException ex = thrown()
            ex.message == 'Username and password should be separated by colon'
        where:
            usernamePassword << ['test', ':test', 'test:', ':', 'test::', 'test:test:', ':test:test']
    }

}
