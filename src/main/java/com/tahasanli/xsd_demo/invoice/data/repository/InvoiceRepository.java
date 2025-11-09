package com.tahasanli.xsd_demo.invoice.data.repository;

import com.tahasanli.xsd_demo.invoice.data.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
}
