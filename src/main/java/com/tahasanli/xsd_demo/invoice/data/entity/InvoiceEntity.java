package com.tahasanli.xsd_demo.invoice.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INVOICE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NIP")
    private String nip;

    @Column(name = "P1")
    private String p1;

    @Column(name = "P2")
    private String p2;
}
