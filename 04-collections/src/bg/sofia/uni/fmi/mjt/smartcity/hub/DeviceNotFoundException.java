package bg.sofia.uni.fmi.mjt.smartcity.hub;

public class DeviceNotFoundException extends Exception {

    public DeviceNotFoundException(String deviceId) {
        super(String.format("Device with id %s does not exists!", deviceId));
    }
}
