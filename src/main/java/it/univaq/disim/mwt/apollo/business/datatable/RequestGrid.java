package it.univaq.disim.mwt.apollo.business.datatable;

import java.util.List;

import lombok.Data;

@Data
public class RequestGrid {

	private String draw;
	private Long start;
	private Long length;
	private SearchType search;
	private List<OrderType> order;
	private List<ColumnType> columns;
	
	public String getSortCol() {
		OrderType orderType = this.order.get(0);
		return columns.get(orderType.getColumn()).getData();
	}
	
	public String getSortDir() {
		OrderType orderType = this.order.get(0);
		return orderType.getDir();
	}
}
