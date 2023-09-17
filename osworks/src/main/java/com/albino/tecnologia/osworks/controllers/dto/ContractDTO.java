package com.albino.tecnologia.osworks.controllers.dto;


import com.albino.tecnologia.osworks.enums.ContractType;
import com.albino.tecnologia.osworks.infra.jackson.deserializer.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate beginDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @Positive(message = "Fill the value With Positive Number")
    @JsonSerialize
    private BigDecimal unitValue;

    @Positive(message = "Fill the Function Points field with positive number")
    private Long numberFunctionPoints;

    @NotNull(message = "Fill o Description Correctly")
    private List<String> descriptions;

    private List<ContractType> contractTypes;

    @Positive(message = "Company ID must be positive")
    private Long companyId;

    @Positive(message = "Responsible ID must be positive")
    private Long responsibleId;

}
