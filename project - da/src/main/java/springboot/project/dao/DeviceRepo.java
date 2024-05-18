package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.Device;

public interface DeviceRepo extends JpaRepository<Device, Integer> {
    Device findByDeviceId(String deviceId);
}
