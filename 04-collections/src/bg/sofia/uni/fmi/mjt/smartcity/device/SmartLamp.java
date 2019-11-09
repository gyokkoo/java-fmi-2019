package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartLamp extends Device {

    private static int deviceNumber = 0;

    private String id;

    public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        this.id = String.format("%s-%s-%d", DeviceType.LAMP.getShortName(), name, deviceNumber++);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.LAMP;
    }
}