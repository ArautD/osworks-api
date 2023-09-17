package com.albino.tecnologia.osworks.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String osCode;

    private String description;

    private String hoursNumber;

    private Integer numberFunctionPoints;

    private LocalDate openDate;

    private String status;

    @OneToOne
    private Responsible responsible;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;


    @JsonIgnoreProperties({"contract"})
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

}
