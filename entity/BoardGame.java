package board.game.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class BoardGame {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardGameId;
	private String boardGameName;
	private String minPlayers;
	private String maxPlayers;
	private String description;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "boardGame", cascade = CascadeType.ALL)
	private Set<Genere> genere = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "boardGames", cascade = CascadeType.ALL)
	private Set <BoardGameStore> boardGameStores = new HashSet<>();
	
	
}
