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
		handleException(request, ex);
		return "/utility/error_404";
	}

	@ExceptionHandler({BusinessException.class, Exception.class})
	public String handleBusinessException(HttpServletRequest request, Exception ex, Model model) {
		handleException(request, ex);
		return "/utility/error_500";
	}

	private void handleException(HttpServletRequest request, Exception ex) {
		log.info("[Exception Occured]:: URL=" + request.getRequestURL() + ", method=" + request.getMethod() + ", message=" + ex.getMessage());
		log.info("[Exception Class Name]:: " + ex.getClass().getName());

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		printWriter.flush();
	}
}
