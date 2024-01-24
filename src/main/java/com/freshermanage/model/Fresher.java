package com.freshermanage.model;


import javax.persistence.*;

@Entity
@Table(name = "Fresher")
public class Fresher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    protected String gender;
    @Column(name = "age")
    private int age;
    @Column(name = "address")
    private String address;
    @Column(name = "language")
    private String language;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "point1")
    private float point1;
    @Column(name = "point2")
    private float point2;
    @Column(name = "point3")
    private float point3;
    @ManyToOne
    private Center center;
    @Column(name = "averagePoint")
    private float averagePoint;

    public Fresher() {
    }

    public Fresher(long id, String name, String gender, int age, String address, String language, String email, float point1, float point2, float point3, Center center, float averagePoint) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.language = language;
        this.email = email;
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.center = center;
        this.averagePoint = averagePoint;
    }


    @PrePersist
    public void calculateAveragePoint() {
        this.averagePoint = (point1 + point2 + point3) / 3.0f;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getPoint1() {
        return point1;
    }

    public void setPoint1(float point1) {
        this.point1 = point1;
    }

    public float getPoint2() {
        return point2;
    }

    public void setPoint2(float point2) {
        this.point2 = point2;
    }

    public float getPoint3() {
        return point3;
    }

    public void setPoint3(float point3) {
        this.point3 = point3;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public float getAveragePoint() {
        return averagePoint;
    }

    public void setAveragePoint(float averagePoint) {
        this.averagePoint = averagePoint;
    }
}
