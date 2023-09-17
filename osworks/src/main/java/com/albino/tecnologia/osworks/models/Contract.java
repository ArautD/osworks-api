package com.albino.tecnologia.osworks.models;

import com.albino.tecnologia.osworks.enums.ContractType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractCode;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    private LocalDate beginDate;

    private LocalDate endDate;

    private Long numberFunctionPoints;

    private Long numberFunctionPointsTotal;

    private BigDecimal unitValue;

    private BigDecimal unitValueTotal;

    @ElementCollection
    @Column(name = "description",columnDefinition = "TEXT")
    private List<String> descriptions = new ArrayList<>();

    private String status;

    @ElementCollection
    @Column(name = "TIPO_CONTRATO",columnDefinition = "TEXT")
    private List<ContractType> contractTypes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contract_id")
    @JsonIgnoreProperties("contract")
    private List<ServiceOrder> os = new ArrayList<>();


    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("contract")
    private List<Additive> additives = new ArrayList<>();

}
