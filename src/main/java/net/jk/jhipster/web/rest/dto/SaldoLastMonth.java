package net.jk.jhipster.web.rest.dto;

import java.time.LocalDate;

public class SaldoLastMonth {

	private LocalDate month;

	private Double importe;

	public SaldoLastMonth(LocalDate month, Double importe) {
		super();
		this.month = month;
		this.importe = importe;
	}

	public LocalDate getMonth() {
		return month;
	}

	public void setMonth(LocalDate month) {
		this.month = month;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "SaldoLastMonth {month=" + month + ", importe=" + importe + "}";
	}

}
