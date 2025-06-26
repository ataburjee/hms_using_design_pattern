package com.hms.controller.ipd;

import com.hms.model.opd.Admission;
import com.hms.model.opd.Ward;
import com.hms.service.ipd.IpdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ipd")
public class IpdController {

    @Autowired
    private IpdService ipdService;

    @PostMapping("/ward")
    public Ward addWard(@RequestParam String name, @RequestParam String type, @RequestParam int totalBeds) {
        return ipdService.addWardWithBeds(name, type, totalBeds);
    }

    @PostMapping("/admit")
    public Admission admit(@RequestParam String patientId, @RequestParam String doctorId, @RequestParam Long wardId) {
        return ipdService.admitPatient(patientId, doctorId, wardId);
    }

    @PutMapping("/discharge")
    public Admission discharge(@RequestParam String patientId) {
        return ipdService.dischargePatient(patientId);
    }
}

