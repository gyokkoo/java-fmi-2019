package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.util.*;

public class SmartCityHub {

    private Map<String, SmartDevice> devices = new LinkedHashMap<>();

    public SmartCityHub() {
    }

    /**
     * Adds a @device to the SmartCityHub.
     *
     * @throws IllegalArgumentException         in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        if (device == null) {
            throw new IllegalArgumentException("Smart device cannot be null!");
        }

        if (devices.containsKey(device.getId())) {
            throw new DeviceAlreadyRegisteredException(
                    String.format("Device with id %s already exists", device.getId()));
        }

        devices.put(device.getId(), device);
    }

    /**
     * Removes the @device from the SmartCityHub.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException  in case the @device is not found.
     */
    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        if (device == null) {
            throw new IllegalArgumentException("Smart device cannot be null!");
        }

        if (!devices.containsKey(device.getId())) {
            throw new DeviceNotFoundException("Smart device ");
        }

        devices.remove(device.getId(), device);
    }

    /**
     * Returns a SmartDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null.
     * @throws DeviceNotFoundException  in case device with ID @id is not found.
     */
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null!");
        }

        if (!devices.containsKey(id)) {
            throw new DeviceNotFoundException(id);
        }

        return devices.get(id);
    }

    /**
     * Returns the total number of devices with type @type registered in SmartCityHub.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        if (type == null) {
            throw new IllegalArgumentException("DeviceType cannot be null");
        }

        int totalDevicesCount = 0;
        for (SmartDevice device : devices.values()) {
            if (type.equals(device.getType())) {
                totalDevicesCount++;
            }
        }

        return totalDevicesCount;
    }

    /**
     * Returns a collection of IDs of the top @n devices which consumed
     * the most power from the time of their installation until now.
     * <p>
     * The total power consumption of a device is calculated by the hours elapsed
     * between the two LocalDateTime-s multiplied by the stated power consumption of the device.
     * <p>
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     * s
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N cannot be a negative number");
        }

        return devices.keySet();
    }

    /**
     * Returns a collection of the first @n registered devices, i.e the first @n that were added
     * in the SmartCityHub (registration != installation).
     * <p>
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N cannot be a negative number");
        }

        return devices.values();
    }
}
