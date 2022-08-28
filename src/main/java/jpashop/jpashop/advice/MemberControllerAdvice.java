package jpashop.jpashop.advice;

import jpashop.jpashop.controller.MemberController;
import jpashop.jpashop.dto.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {MemberController.class})
public class MemberControllerAdvice {

    @ExceptionHandler
    protected ResponseEntity<?> handleMemberException(IllegalArgumentException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .code("MEMBER ERROR")
            .message(e.getMessage())
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
