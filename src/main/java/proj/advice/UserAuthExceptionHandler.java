package proj.advice;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserAuthExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, SignatureException.class, JwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleFailedAuth(Exception exception) {
        var errorMsgMap = new HashMap<String, String>();
        errorMsgMap.put("errorMsg", exception.getMessage());
        return errorMsgMap;
    }

}
