package com.tekworks.rental.entity;
 
import java.time.Instant;
import java.util.List;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cars {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String carName;
	private String carModel;
	private String carImageUrl;
	private Double perDayCharge;
	private List<String> avaibleCities;
	private String registrationNumber;
    private String ownerName;
    private String ownerMobileNumber;

    private Instant createdAt;
    private Instant updatedAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
 
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
 
}