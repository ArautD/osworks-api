package com.albino.tecnologia.osworks.models;

import com.albino.tecnologia.osworks.enums.ContractType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Additive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long functionPoints;

    private LocalDate endDate;

    private BigDecimal unitValue;

    @ElementCollection
    @Column
    private List<ContractType> contractTypes;

    @ElementCollection
    @Column(name = "DESCRICAO",columnDefinition = "TEXT")
    private List<String> descriptions;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

}
