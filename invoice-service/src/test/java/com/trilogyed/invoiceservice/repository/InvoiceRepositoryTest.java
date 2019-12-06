package com.trilogyed.invoiceservice.repository;

import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceItemRepository itemRepository;

    private LocalDate date = LocalDate.now();

    @Before
    public void setUp() throws Exception {
        invoiceRepository.findAll().stream().forEach(invoice -> invoiceRepository.deleteById(invoice.getId()));
        itemRepository.findAll().stream().forEach(invoiceItem -> itemRepository.deleteById(invoiceItem.getInvoice_item_id()));
    }

    @Test
    public void shouldFindInvoicesByCustomerIdAndInvoiceItemsByInvoiceId() {

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(date);
        Invoice invoiceFromDb = invoiceRepository.save(invoice1);
        invoice1.setId(invoiceFromDb.getId());

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(2);
        invoice2.setPurchaseDate(date);
        Invoice invoiceFromDb2 = invoiceRepository.save(invoice2);
        invoice2.setId(invoiceFromDb2.getId());

        Invoice invoice3 = new Invoice();
        invoice3.setCustomerId(1);
        invoice3.setPurchaseDate(date);
        Invoice invoiceFromDb3 = invoiceRepository.save(invoice3);
        invoice3.setId(invoiceFromDb3.getId());

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInventory_id(1);
        invoiceItem1.setQuantity(1);
        invoiceItem1.setInvoiceId(invoice1.getId());
        invoiceItem1.setUnitPrice(new BigDecimal("22.12"));
        invoiceItem1.setInvoice_item_id(itemRepository.save(invoiceItem1).getInvoice_item_id());

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInventory_id(1);
        invoiceItem2.setQuantity(1);
        invoiceItem2.setInvoiceId(invoice2.getId());
        invoiceItem2.setUnitPrice(new BigDecimal("22.12"));
        invoiceItem2.setInvoice_item_id(itemRepository.save(invoiceItem2).getInvoice_item_id());

        InvoiceItem invoiceItem3 = new InvoiceItem();
        invoiceItem3.setInventory_id(1);
        invoiceItem3.setQuantity(1);
        invoiceItem3.setInvoiceId(invoice3.getId());
        invoiceItem3.setUnitPrice(new BigDecimal("22.12"));
        invoiceItem3.setInvoice_item_id(itemRepository.save(invoiceItem3).getInvoice_item_id());

        invoice1.setInvoiceItems(itemRepository.findAllByInvoiceId(invoice1.getId()));
        invoice2.setInvoiceItems(itemRepository.findAllByInvoiceId(invoice2.getId()));
        invoice3.setInvoiceItems(itemRepository.findAllByInvoiceId(invoice3.getId()));

        assertEquals(new ArrayList<InvoiceItem>(Arrays.asList(invoiceItem1)), itemRepository.findAllByInvoiceId(invoice1.getId()));
        assertEquals(new ArrayList<InvoiceItem>(Arrays.asList(invoiceItem2)), itemRepository.findAllByInvoiceId(invoice2.getId()));
        assertEquals(new ArrayList<InvoiceItem>(Arrays.asList(invoiceItem3)), itemRepository.findAllByInvoiceId(invoice3.getId()));

        List<Invoice> expected1 = new ArrayList<>(Arrays.asList(invoice1, invoice3));
        List<Invoice> expected2 = new ArrayList<>(Arrays.asList(invoice2));
        List<Invoice> empty = new ArrayList<>();

        assertEquals(expected1, invoiceRepository.findInvoicesByCustomerId(1));
        assertEquals(expected2, invoiceRepository.findInvoicesByCustomerId(2));
        assertEquals(empty, invoiceRepository.findInvoicesByCustomerId(500));
        assertEquals(empty, invoiceRepository.findInvoicesByCustomerId(-5));
    }
}