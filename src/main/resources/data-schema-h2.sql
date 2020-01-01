--drop table stock_quote;

create table stock_quote (
	trade_date date,
	open numeric,
	high numeric,
	low numeric,
	close numeric,
	volume numeric,
	dividend numeric,
	split numeric
);
	
	