package com.lsq.supplier.api.model;

import java.math.BigDecimal;

public class SupplierInvoiceSummary {

	private String supplierId;
	private long totalInvoices;
	private long openInvoices;
	private long lateInvoices;
	private BigDecimal openInvoicesAmount;
	private BigDecimal lateInvoicesAmount;

	public SupplierInvoiceSummary() {
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public long getTotalInvoices() {
		return totalInvoices;
	}

	public void setTotalInvoices(long totalInvoices) {
		this.totalInvoices = totalInvoices;
	}

	public long getOpenInvoices() {
		return openInvoices;
	}

	public void setOpenInvoices(long openInvoices) {
		this.openInvoices = openInvoices;
	}

	public long getLateInvoices() {
		return lateInvoices;
	}

	public void setLateInvoices(long lateInvoices) {
		this.lateInvoices = lateInvoices;
	}

	public BigDecimal getOpenInvoicesAmount() {
		return openInvoicesAmount;
	}

	public void setOpenInvoicesAmount(BigDecimal openInvoicesAmount) {
		this.openInvoicesAmount = openInvoicesAmount;
	}

	public BigDecimal getLateInvoicesAmount() {
		return lateInvoicesAmount;
	}

	public void setLateInvoicesAmount(BigDecimal lateInvoicesAmount) {
		this.lateInvoicesAmount = lateInvoicesAmount;
	}

	
}
