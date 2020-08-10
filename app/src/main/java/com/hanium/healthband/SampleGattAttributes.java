package com.hanium.healthband;

import java.util.HashMap;
/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String TEMPERATURE_MEASUREMENT = "00002a6e-0000-1000-8000-00805f9b34fb";
    public static String HUMIDITY_MEASUREMENT = "00002a6f-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static String EXTRA_SERVICE = "d2ff1355-e7b4-4ba6-829f-a3af2a616ec4";
    public static String EXTRA_CHAR = "9c583724-c1dc-471a-8fc1-8558059dc2c5";

    static {
        // Sample Services.

        // Sample Characteristics.
        attributes.put(TEMPERATURE_MEASUREMENT, "Temperature Measurement");
        attributes.put(HUMIDITY_MEASUREMENT, "Humidity Measurement");
        attributes.put(EXTRA_SERVICE, "EXTRA SERVICE");
        attributes.put(EXTRA_CHAR, "EXTRA Measurement");
    }
    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}