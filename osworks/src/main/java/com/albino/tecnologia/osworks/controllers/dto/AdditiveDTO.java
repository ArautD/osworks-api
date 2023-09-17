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
public class AdditiveDTO {

    @Positive(message = "Fill in the Function Points Field with a Positive Number")
    private Long functionPoints;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @Positive(message = "Fill Value With Positive Number")
    @JsonSerialize
    private BigDecimal unitValue;

    @NotNull(message = "Fill In The Contract Type Correctly")
    private List<ContractType> contractTypes;

    @NotNull(message = "Fill in the Description Correctly")
    private List<String> descriptions;
}
