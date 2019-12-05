package com.trilogyed.invoiceservice.repository;

import com.trilogyed.invoiceservice.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Integer> {
    public List<InvoiceItem> findAllByInvoiceId(int id);
}
