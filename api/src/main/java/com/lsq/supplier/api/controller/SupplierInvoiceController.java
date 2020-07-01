package com.lsq.supplier.api.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lsq.supplier.api.model.SupplierInvoice;
import com.lsq.supplier.api.model.SupplierInvoiceSummary;
import com.lsq.supplier.api.repo.SupplierInvoiceRepository;

@RestController
@RequestMapping(path = "/supplier/invoice")
public class SupplierInvoiceController {

	@Autowired // This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	private SupplierInvoiceRepository supplierInvoiceRepository;

	@GetMapping("/health-check")
	public boolean healthCheck() {
		return true;
	}

	@PostMapping("/bulk/csv")
	public boolean handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		String[] HEADERS = { "Supplier Id", "Invoice Id", "Invoice Date", "Invoice Amount", "Terms", "Payment Date",
				"Payment Amount" };

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {

			//I would save the file here but not sure where to put it on another local
			
			CSVParser csvParser = CSVFormat.DEFAULT.withHeader(HEADERS)
					.parse(new InputStreamReader(file.getInputStream()));

			for (CSVRecord record : csvParser) {
				try {
					String supplierId = record.get("Supplier Id");
					String invoiceId = record.get("Invoice Id");

					Date invoiceDate = StringUtils.isEmpty(record.get("Invoice Date")) ? null
							: sdf.parse(record.get("Invoice Date"));
					BigDecimal invoiceAmount = new BigDecimal(record.get("Invoice Amount")).setScale(2,
							RoundingMode.HALF_UP);
					int terms = Integer.valueOf(record.get("Terms"));
					Date paymentDate = StringUtils.isEmpty(record.get("Payment Date")) ? null
							: sdf.parse(record.get("Payment Date"));
					BigDecimal paymentAmount = StringUtils.isEmpty(record.get("Payment Amount")) ? null
							: new BigDecimal(record.get("Payment Amount")).setScale(2, RoundingMode.HALF_UP);

					SupplierInvoice si = new SupplierInvoice(supplierId, invoiceId, invoiceDate, invoiceAmount, terms,
							paymentDate, paymentAmount);
					// SupplierInvoice si = new SupplierInvoice(supplierId, invoiceId);
					supplierInvoiceRepository.save(si);
				} catch (NumberFormatException | ParseException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@PostMapping("/SupplierSummary")
	public SupplierInvoiceSummary listSupplierSummary(@RequestParam("supplierId") String supplierId) {
		
		List<SupplierInvoice> list = supplierInvoiceRepository.findBySupplierId(supplierId);

		Map<String, Long> counts = list.stream()
				.collect(Collectors.groupingBy(SupplierInvoice::getState, Collectors.counting()));
		Map<String, BigDecimal> totals = list.stream().collect(Collectors.groupingBy(SupplierInvoice::getState,
				Collectors.mapping(SupplierInvoice::getInvoiceAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

		SupplierInvoiceSummary ssi = new SupplierInvoiceSummary();

		long open = counts.containsKey("OPEN") ? counts.get("OPEN") : 0;
		long late = counts.containsKey("LATE") ? counts.get("LATE") : 0;
		BigDecimal openAmount = totals.containsKey("OPEN") ? totals.get("OPEN") : BigDecimal.ZERO;
		BigDecimal lateAmount = totals.containsKey("LATE") ? totals.get("LATE") : BigDecimal.ZERO;

		ssi.setSupplierId(supplierId);
		ssi.setTotalInvoices(list.stream().count());
		ssi.setOpenInvoices(open);
		ssi.setLateInvoices(late);
		ssi.setOpenInvoicesAmount(openAmount);
		ssi.setLateInvoicesAmount(lateAmount);
		

		return ssi;

	}

}