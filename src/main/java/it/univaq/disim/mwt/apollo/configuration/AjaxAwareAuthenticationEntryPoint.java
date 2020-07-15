package it.univaq.disim.mwt.apollo.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

@Slf4j
public class AjaxAwareAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private static final String[] SURVEY_URL_API = new String[] {"/surveys/create", "/surveys/publish", "/surveys/invitationpool", "/surveys/update", "/surveys/delete"};
    private static final String[] QUESTIONGRUP_URL_API = new String[] {"/questiongroups/create", "/questiongroups/update",  "/questiongroups/delete"};
    private static final String[] QUESTION_URL_API = new String[] {"/choicequestion/create", "/choicequestion/update",  "/choicequestion/delete", "/inputquestion/create", "/inputquestion/update",  "/inputquestion/delete", "/matrixquestion/create", "/matrixquestion/update", "/matrixquestion/delete", "/selectionquestion/create", "/selectionquestion/update", "/selectionquestion/delete"};
    private static final String[] ANSWER_URL_API = new String[] {"/apollo/answers/"};

    public AjaxAwareAuthenticationEntryPoint(String loginUrl) {
        super(loginUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("[AjaxAwareAuthenticationEntryPoint]::[RequestURI]:" + request.getRequestURI());

        boolean isAjax = isAjaxRequest(request.getRequestURI());

        if (isAjax) {
            response.sendError(403, "Forbidden");
        } else {
            super.commence(request, response, authException);
        }
    }

    private boolean isAjaxRequest(String urlRequest) {
        for (String url : SURVEY_URL_API) {
            if (urlRequest.endsWith(url)) return true;
        }
        for (String url : QUESTIONGRUP_URL_API) {
            if (urlRequest.endsWith(url)) return true;
        }
        for (String url : QUESTION_URL_API) {
            if (urlRequest.endsWith(url)) return true;
        }
        for (String url : ANSWER_URL_API) {
            if (urlRequest.startsWith(url)) return true;
        }
        return false;
    }
}