package it.univaq.disim.mwt.apollo.domain;

import lombok.Data;

@Data
public class SurveyResponseBody {

    private String msg;
    private int status;
    private Survey result;

}