package it.univaq.disim.mwt.apollo.business.datatable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseGrid<R> {

	private String draw;
	private long recordsTotal;
	private long recordsFiltered;
	private List<R> data;

}
