package it.univaq.disim.mwt.apollo.presentation.model;

import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.Data;

@Data
public class PrivateSurveyCredentials {

    private Survey survey;
    private String email;
    private String password;

}
