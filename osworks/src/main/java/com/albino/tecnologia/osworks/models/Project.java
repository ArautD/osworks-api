package com.albino.tecnologia.osworks.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private LocalDate beginDate;

    private LocalDate endDate;

    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    private ServiceOrder serviceOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserModel projectManager;

}
