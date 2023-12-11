package com.example.gestionemployemachineth.entities;
import jakarta.persistence.*;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Entity
public class Machine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String reference;
    @Temporal(TemporalType.DATE)
    private Date dateAchat;
    private double prix;

    @ManyToOne
    private Employee employeid;

    public Machine() {
    }

    public Machine(Long id) {
        this.id = id;
    }

    public Machine(Long id, String marque, String reference, Date dateAchat, double prix) {
        this.id = id;
        this.marque = marque;
        this.reference = reference;
        this.dateAchat = dateAchat;
        this.prix = prix;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setEmployeid(Employee employeid) {
        this.employeid = employeid;
    }

	public Long getId() {
		return id;
	}

	public String getMarque() {
		return marque;
	}

	public String getReference() {
		return reference;
	}

	public Date getDateAchat() {
		return dateAchat;
	}

	public double getPrix() {
		return prix;
	}

	public Employee getEmployeid() {
		return employeid;
	}

}