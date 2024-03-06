package com.MSGFoundation.controller;

import com.MSGFoundation.model.Couple;
import com.MSGFoundation.dto.CoupleDTO;
import com.MSGFoundation.service.impl.CoupleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/couple")
public class CoupleController {
    private final CoupleServiceImpl coupleService;

    @GetMapping("/")
    public List<Couple> getAllCouples() {
        return coupleService.getAllCouples();
    }

    @GetMapping("/{id}")
    public Couple getCoupleById(@PathVariable Long id) {
        return coupleService.getCoupleById(id);
    }

    @PostMapping("/create")
    public Couple createCouple(@RequestBody CoupleDTO coupleDTO) {
        return coupleService.createCouple(coupleDTO);
    }

    @PutMapping("/update/{id}")
    public Couple updateCouple(@PathVariable Long id, @RequestBody Couple couple) {
        return coupleService.updateCouple(id, couple);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCouple(@PathVariable Long id) {
        coupleService.deleteCouple(id);
    }

    @GetMapping("getByIds/{partnerId1}/{partnerId2}")
    public Long getCoupleByIds(@PathVariable String partnerId1, @PathVariable String partnerId2){
        return coupleService.getCouplebyIds(partnerId1,partnerId2);
    }

    public ResponseEntity<Long> getCouplebyIds(@PathVariable String id1, @PathVariable String id2) {
        Long coupleId = coupleService.getCouplebyIds(id1,id2);
        if (coupleId != null) {
            return ResponseEntity.ok(coupleId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
