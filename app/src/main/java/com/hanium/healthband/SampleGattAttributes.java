package com.hanium.healthband;

import java.util.HashMap;
/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a6e-0000-1000-8000-00805f9b34fb";
    public static String HUMIDITY_MEASUREMENT = "00002a6f-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    static {
        // Sample Services.
        attributes.put("0000181a-0000-1000-8000-00805f9b34fb", "Environment Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a6f-0000-1000-8000-00805f9b34fb", "Temperature Measurement");
    }
    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}