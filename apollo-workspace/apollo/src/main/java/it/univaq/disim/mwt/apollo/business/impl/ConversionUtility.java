package it.univaq.disim.mwt.apollo.business.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;

public class ConversionUtility {
	
	public static Pageable requestGrid2Pageable(RequestGrid requestGrid) {
        return PageRequest.of((requestGrid.getStart().intValue() / requestGrid.getLength().intValue()), requestGrid.getLength().intValue(),
                "asc".equals(requestGrid.getSortDir()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                requestGrid.getSortCol());
    }
}
