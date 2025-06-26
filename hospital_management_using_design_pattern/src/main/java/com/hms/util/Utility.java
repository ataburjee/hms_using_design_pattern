package com.hms.util;

import java.util.UUID;

public class Utility {

    public static final String DOCTOR = "DOC";
    public static final String PATIENT = "PTN";
    public static final String TIME_SLOT = "TMS";
    public static final String prescription = "PRE";
    public static final String appointment = "APN";
    public static final String PRESCRIPTION_ITEM = "PIT";
    public static final String OPD = "OPD";
    public static final String MEDICINE = "MED";
    public static final String ADMISSION = "ADM";
    public static final String USER = "USR";

    public static String generateId(String user) {
        return "HMS" + user + "-" + UUID.randomUUID().toString().toUpperCase();
    }

}
