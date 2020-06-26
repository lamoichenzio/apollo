package it.univaq.disim.mwt.apollo.presentation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler({NotFoundException.class, NoSuchElementException.class})
	public String handleNotFoundException(HttpServletRequest request, Exception ex, Model model) {
		log.info("[Exception Occured]:: URL=" + request.getRequestURL() + ", method=" + request.getMethod() + ", message=" + ex.getMessage());
		log.info("[Exception Class Name]:: " + ex.getClass().getName());

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		printWriter.flush();
		model.addAttribute("errorMessage", stringWriter.toString());

		return "/pages/error_500";
	}

	@ExceptionHandler({BusinessException.class, Exception.class})
	public String handleBusinessException(HttpServletRequest request, Exception ex, Model model) {
		log.info("[Exception Occured]:: URL=" + request.getRequestURL() + ", method=" + request.getMethod() + ", message=" + ex.getMessage());
		log.info("[Exception Class Name]:: " + ex.getClass().getName());

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		ex.printStackTrace(printWriter);
		printWriter.flush();
		String message = (ex.getCause() == null) ? "" : ex.getCause().getMessage();
		model.addAttribute("errorCause", message);
		model.addAttribute("errorMessage", stringWriter.toString());
		return "/pages/error_500";
	}
	
}
