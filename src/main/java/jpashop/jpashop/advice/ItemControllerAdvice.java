package jpashop.jpashop.advice;

import jpashop.jpashop.controller.ItemController;
import jpashop.jpashop.controller.MemberController;
import jpashop.jpashop.dto.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ItemController.class})
public class ItemControllerAdvice {

    @ExceptionHandler
    protected ResponseEntity<?> handleMemberException(IllegalArgumentException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .code("ITEM ERROR")
            .message(e.getMessage())
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
