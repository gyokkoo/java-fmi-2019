package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public abstract class Device implements SmartDevice {

    private String name;
    private double powerConsumption;
    private LocalDateTime installationDateTime;

    protected Device(String name, double powerConsumption, LocalDateTime installationDateTime) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = installationDateTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumption;
    }

    @Override
    public LocalDateTime getInstallationDateTime() {
        return installationDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Device other = (Device) obj;
        return this.getId().equals(other.getId());
    }

    @Override
    public int compareTo(Object o) {
        SmartDevice otherDevice = (SmartDevice) o;
        return Double.compare(this.getPowerConsumption(), otherDevice.getPowerConsumption());
    }
}
