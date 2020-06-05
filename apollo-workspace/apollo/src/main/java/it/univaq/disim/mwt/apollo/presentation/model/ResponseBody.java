package it.univaq.disim.mwt.apollo.presentation.model;

import lombok.Data;

@Data
public class ResponseBody {
	
	private String msg;
	private ResponseStatus status;
}
