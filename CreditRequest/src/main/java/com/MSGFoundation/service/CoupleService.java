package com.MSGFoundation.service;

import com.MSGFoundation.dto.CoupleDTO;
import com.MSGFoundation.model.Couple;

import java.util.List;

public interface CoupleService {
    List<Couple> getAllCouples();
    Couple getCoupleById(Long id);
    Couple createCouple(CoupleDTO coupleDTO);
    Couple updateCouple(Long id, Couple couple);
    void deleteCouple(Long id);
    Long getCouplebyIds(String partnerId1, String partnerId2);
}
