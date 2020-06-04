package it.univaq.disim.mwt.apollo.presentation.model;

import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.Data;

@Data
public class SurveyResponseBody {

    private String msg;
    private int status;
    private Survey result;

}