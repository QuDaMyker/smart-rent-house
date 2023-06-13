package com.example.renthouse.OOP;

public class LocationTemp {
    private String street;
    private String address;
    private City city;
    private District district;
    private Ward ward;

    public LocationTemp() {
    }

    public LocationTemp(String street, String address, City city, District district, Ward ward) {
        this.street = street;
        this.address = address;
        this.city = city;
        this.district = district;
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String LocationToString(){
        String res = address + " Đường " + street + ", "
                + ward.getName_with_type() + ", "
                + district.getName_with_type() + ", "
                + city.getName_with_type() + ", Vietnam";
        return res;
    }
}
