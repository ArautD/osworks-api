package com.albino.tecnologia.osworks.models;

import com.albino.tecnologia.osworks.enums.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cnpj;

    private CompanyType companyType;

    private String companyName;

    private String stateRegistration;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Responsible responsible;

    private String phoneNumber;

    private String email;

    private LocalDate bornDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

    private String status;
}
