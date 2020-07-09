package it.univaq.disim.mwt.apollo.presentation;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler({NotFoundException.class, NoSuchElementException.class})
	public String handleNotFoundException(HttpServletRequest request, Exception ex) {
		log.info("[Exception Occured]::[404]::URL=" + request.getRequestURL() + ", method=" + request.getMethod() + ", message=" + ex.getMessage());
		log.info("[Exception Class Name]:: " + ex.getClass().getName());
		return "/utility/error_404";
	}

	@ExceptionHandler({BusinessException.class, Exception.class})
	public String handleBusinessException(HttpServletRequest request, Exception ex) {
		log.info("[Exception Occured]::[500]::URL=" + request.getRequestURL() + ", method=" + request.getMethod() + ", message=" + ex.getMessage());
		log.info("[Exception Class Name]:: " + ex.getClass().getName());
		return "/utility/error_500";
	}
	
}
