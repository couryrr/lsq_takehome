package com.lsq.supplier.api.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SupplierInvoice {

	enum State {
		OPEN, CLOSED, LATE, PAYMENT_SCHEDULED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String supplierId;
	private String invoiceId;
	private Date invoiceDate;
	private BigDecimal invoiceAmount;
	private int terms;
	private Date paymentDate;
	private BigDecimal paymentAmount;
	private String state;

	public SupplierInvoice() {
	}

	public SupplierInvoice(String supplierId, String invoiceId) {

		this.supplierId = supplierId;
		this.invoiceId = invoiceId;
	}
	public SupplierInvoice(String supplierId, String invoiceId, Date invoiceDate, BigDecimal invoiceAmount, int terms,
			Date paymentDate, BigDecimal paymentAmount) {

		this.supplierId = supplierId;
		this.invoiceId = invoiceId;
		this.invoiceDate = invoiceDate;
		this.invoiceAmount = invoiceAmount;
		this.terms = terms;
		this.paymentDate = paymentDate;
		this.paymentAmount = paymentAmount;

		Date currentDate = new Date();

		/**
		 * Open (invoice is unpaid/partially paid) 
		 * Closed (invoice is paid in full,payment date in the past)
		 * Late (invoice has past due date without payment)
		 * Payment Scheduled (invoice has payment set for future date)
		 */
		
		
		if(paymentAmount == null || paymentAmount.compareTo(invoiceAmount) > 0) {
			this.setState(State.OPEN.name());
		}
		
		if(paymentAmount != null && invoiceAmount.compareTo(paymentAmount) <= 0) {
			this.setState(State.CLOSED.name());
		}
		
		if(paymentAmount == null && invoiceDate.compareTo(currentDate) < 0) {
			this.setState(State.LATE.name());
		}
		
		if(paymentDate != null && paymentDate.compareTo(currentDate) > 0) {
			this.setState(State.PAYMENT_SCHEDULED.name());
		}
		
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public int getTerms() {
		return terms;
	}

	public void setTerms(int terms) {
		this.terms = terms;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
