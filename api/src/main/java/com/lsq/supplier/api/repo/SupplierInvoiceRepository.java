package com.lsq.supplier.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lsq.supplier.api.model.SupplierInvoice;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SupplierInvoiceRepository extends CrudRepository<SupplierInvoice, Integer> {
	
	@Query("SELECT si FROM SupplierInvoice si where si.supplierId = :supplierId") 
	List<SupplierInvoice> findBySupplierId(@Param("supplierId") String supplierId);

}