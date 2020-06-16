package it.univaq.disim.mwt.apollo.presentation.model;

import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SurveyResponseBody extends ResponseBodyGeneric {

    private Survey result;

}