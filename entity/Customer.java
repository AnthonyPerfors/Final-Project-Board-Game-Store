package board.game.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerID;
	private String customerFirstName;
	private String customerLastName;
	private String customerPhoneNumber;
	
	@Column(unique = true)
	private String customerEmail;
	
	
}
