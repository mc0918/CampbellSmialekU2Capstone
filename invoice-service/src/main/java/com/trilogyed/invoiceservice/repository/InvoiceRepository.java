package com.trilogyed.invoiceservice.repository;

import com.trilogyed.invoiceservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    public List<Invoice> findInvoicesByCustomerId(int id);
}
