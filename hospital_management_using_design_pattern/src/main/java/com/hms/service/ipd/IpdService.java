package com.hms.service.ipd;

import com.hms.model.opd.Admission;
import com.hms.model.opd.Ward;

public interface IpdService {
    Ward addWardWithBeds(String name, String type, int totalBeds);
    Admission admitPatient(String patientId, String doctorId, Long wardId);
    Admission dischargePatient(String patientId);
}
