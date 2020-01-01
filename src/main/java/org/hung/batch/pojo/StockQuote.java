package org.hung.batch.pojo;

import java.time.LocalDate;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class StockQuote {

	public static final String[] fields = new String[] {
		"date","open","high","low","close","volume","dividend","split","adj_open","adj_high","adj_low","adj_close","adj_volume"
	};
	
	private LocalDate date;
	
	@Min(value=200)
	private Double open;
	
	private Double high;
	private Double low;
	private Double close;
	private Double volume;
	private Double dividend;
	private Double split;
	
}
