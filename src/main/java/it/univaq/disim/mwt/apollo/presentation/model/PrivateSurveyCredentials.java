package it.univaq.disim.mwt.apollo.presentation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateSurveyCredentials {

    private String surveyId;
    private String email;
    private String password;

}
