package it.univaq.disim.mwt.apollo.presentation.model;

import lombok.Data;

@Data
public class GenericResponseBody {
	
	private String msg;
	private ResponseStatus status;
}
