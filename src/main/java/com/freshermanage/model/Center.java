package com.freshermanage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Center")
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "NameCenter")
    private String nameCenter;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Email")
    private String email;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCenterId")
    @JsonBackReference
    private Center parentCenter;

    @OneToMany(mappedBy = "parentCenter")
    @Column(name = "childCenter")
    private Set<Center> childCenter;


    public Center() {
    }

    public Center(long id, String nameCenter, String phone, String email) {
        this.id = id;
        this.nameCenter = nameCenter;
        this.phone = phone;
        this.email = email;
    }

    public Center(long id, String nameCenter, String phone, String email, Center parentCenter, Set<Center> childCenter) {
        this.id = id;
        this.nameCenter = nameCenter;
        this.phone = phone;
        this.email = email;
        this.parentCenter = parentCenter;
        this.childCenter = childCenter;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameCenter() {
        return nameCenter;
    }

    public void setNameCenter(String nameCenter) {
        this.nameCenter = nameCenter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Center getParentCenter() {
        return parentCenter;
    }

    public void setParentCenter(Center parentCenter) {
        this.parentCenter = parentCenter;
    }

    public Set<Center> getChildCenter() {
        return childCenter;
    }

    public void setChildCenter(Set<Center> childCenter) {
        this.childCenter = childCenter;
    }

}
