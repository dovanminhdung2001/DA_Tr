package springboot.project.model;

import springboot.project.entity.DoctorUser;

import java.util.Date;

public class RegisterDTO {
    private String name;
    private Date birthDate;
    private String cccd;
    private String address;
    private DoctorUser doctorUser;
    private String type;
    private String pathological;

    public RegisterDTO() {
    }

    public RegisterDTO(String name, Date birthDate, String cccd, String address,
                       DoctorUser doctorUser, String type, String pathological) {
        this.name = name;
        this.birthDate = birthDate;
        this.cccd = cccd;
        this.address = address;
        this.doctorUser = doctorUser;
        this.type = type;
        this.pathological = pathological;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCCCD() {
        return cccd;
    }

    public void setCCCD(String cccd) {
        this.cccd = cccd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DoctorUser getDoctorUser() {
        return doctorUser;
    }

    public void setDoctorUser(DoctorUser doctorUser) {
        this.doctorUser = doctorUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPathological() {
        return pathological;
    }

    public void setPathological(String pathological) {
        this.pathological = pathological;
    }

}
