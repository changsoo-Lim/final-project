package kr.or.ddit.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CommonExceptionAdvice {

    // 404 에러 처리
//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handle404(NoHandlerFoundException nfe, Model model) {
//        model.addAttribute("errorMessage", "해당 페이지를 찾을 수 없습니다.");
//        return "/stackUp/error/error_404"; // 404 에러 페이지 뷰
//    }

    // 500 에러 처리
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handle500(Exception ex, Model model) {
//        model.addAttribute("errorMessage", "서버 내부 오류가 발생했습니다.");
//        model.addAttribute("exception", ex.getMessage());
//        return "/stackUp/error/error_500"; // 500 에러 페이지 뷰
//    }
}
