package com.albino.tecnologia.osworks.services;

import com.albino.tecnologia.osworks.controllers.dto.AdditiveDTO;
import com.albino.tecnologia.osworks.models.Additive;

public interface AdditiveService {

    Additive addEndDate(Long id, AdditiveDTO additiveDTO);
    Additive addUnitValue(Long id, AdditiveDTO additiveDTO);
    Additive addDescriptions(Long id, AdditiveDTO additiveDTO);
    Additive addTypeContract(Long id, AdditiveDTO additiveDTO);

}
