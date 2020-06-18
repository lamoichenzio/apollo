package it.univaq.disim.mwt.apollo.presentation.helpers;

import java.util.Random;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import it.univaq.disim.mwt.apollo.domain.User;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionType;
import it.univaq.disim.mwt.apollo.security.UserDetailsImpl;

public class Utility {

	/**
	 * Get current user.
	 * @return User
	 */
	public static User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
			return ((UserDetailsImpl) usernamePasswordAuthenticationToken.getPrincipal()).getUser();
		} else {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Get question type.
	 * @param question Question
	 * @return QuestionType
	 */
	public static QuestionType getQuestionType(Question question) {
		if (question instanceof InputQuestion) return QuestionType.INPUT;
		if (question instanceof SelectionQuestion) return QuestionType.SELECTION;
		if (question instanceof MatrixQuestion) return QuestionType.MATRIX;
		if (question instanceof ChoiceQuestion) return QuestionType.CHOICE;
		return null;
	}
	
	/**
	 * Generating random alphanimeric string.
	 * @return String
	 */
	public static String generatingRandomAlphanumericString() {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 12;
	    Random random = new Random();
	 
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	 
	    return generatedString;
	}
}
