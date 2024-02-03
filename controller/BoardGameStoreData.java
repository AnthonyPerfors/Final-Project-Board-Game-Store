package board.game.store.controller;

import java.util.HashSet;
import java.util.Set;

import board.game.store.entity.BoardGame;
import board.game.store.entity.BoardGameStore;
import board.game.store.entity.Customer;
import board.game.store.entity.Genere;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardGameStoreData {

	private Long boardGameStoreId;
	private String boardGameStoreName;
	private String boardGameStoreCity;
	private String boardGameStoreState;
	private String boardGameStoreNumber;
	Set<BoardGameStoreBoardGame> boardGames = new HashSet<>();
	
	
	public BoardGameStoreData(BoardGameStore boardGameStore) {
		
		boardGameStoreId = boardGameStore.getBoardGameStoreId();
		boardGameStoreName = boardGameStore.getBoardGameStoreName();
		boardGameStoreCity = boardGameStore.getBoardGameStoreCity();
		boardGameStoreState = boardGameStore.getBoardGameStoreState();
		boardGameStoreNumber = boardGameStore.getBoardGameStoreNumber();
	
		for(BoardGame boardGame : boardGameStore.getBoardGames()) {
			boardGames.add(new BoardGameStoreBoardGame(boardGame));
		}

		
	}
	
	@Data
	@NoArgsConstructor
	public static class BoardGameStoreBoardGame {
		
		private Long boardGameId;
		private String boardGameName;
		private String minPlayers;
		private String maxPlayers;
		private String description;
		Set<BoardGameGenere> generes = new HashSet<>();
		
		
		public BoardGameStoreBoardGame (BoardGame boardGame) {
			boardGameId = boardGame.getBoardGameId();
			boardGameName = boardGame.getBoardGameName();
			minPlayers = boardGame.getMinPlayers();
			maxPlayers = boardGame.getMaxPlayers();
			description = boardGame.getDescription();
			
			for(Genere genere : boardGame.getGenere()) {
				generes.add(new BoardGameGenere(genere));
			}
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class BoardGameGenere {
		
		private Long genereId;
		private String category;
		
		public BoardGameGenere (Genere genere) {
			genereId = genere.getGenereId();
			category = genere.getCategory();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class BoardGameStoreCustomers {
		
		private Long customerID;
		private String customerFirstName;
		private String customerLastName;
		private String customerPhoneNumber;
		private String customerEmail;
		
		public BoardGameStoreCustomers (Customer customer) {
			customerID = customer.getCustomerID();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerPhoneNumber = customer.getCustomerPhoneNumber();
			customerEmail = customer.getCustomerEmail();
		}
	}
	
}
