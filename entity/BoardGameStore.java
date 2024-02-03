package board.game.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class BoardGameStore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardGameStoreId;
	private String boardGameStoreName;
	private String boardGameStoreCity;
	private String boardGameStoreState;
	private String boardGameStoreNumber;
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "store_board_game", joinColumns = @JoinColumn(name = "board_game_store_id"),
	inverseJoinColumns = @JoinColumn(name = "board_game_id"))
	private Set<BoardGame> boardGames = new HashSet<>();
}
