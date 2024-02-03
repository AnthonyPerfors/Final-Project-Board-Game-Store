package board.game.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import board.game.store.controller.BoardGameStoreData.BoardGameGenere;
import board.game.store.controller.BoardGameStoreData.BoardGameStoreBoardGame;
import board.game.store.controller.BoardGameStoreData.BoardGameStoreCustomers;
import board.game.store.service.BoardGameStoreService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board_game_store")
@Slf4j
public class BoardGameController {

	@Autowired
	private BoardGameStoreService boardGameStoreService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public BoardGameStoreData createBoardGameStore(@RequestBody BoardGameStoreData boardGameStoreData) {
		log.info("Creating Board Game Store {}", boardGameStoreData);
		return boardGameStoreService.saveBoardGameStore(boardGameStoreData);
	}
	
	@PutMapping("/{boardGameStoreId}")
	public BoardGameStoreData updateBoardGameStore(@PathVariable Long boardGameStoreId, @RequestBody BoardGameStoreData boardGameStoreData) {
		boardGameStoreData.setBoardGameStoreId(boardGameStoreId);
		log.info("Updating board game store {}", boardGameStoreData);
		return boardGameStoreService.saveBoardGameStore(boardGameStoreData);
	}
	
	@PostMapping("{boardGameStoreId}/boardGame")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BoardGameStoreBoardGame insertBoardGame(@PathVariable Long boardGameStoreId, @RequestBody BoardGameStoreBoardGame boardGameStoreBoardGame) {
		log.info("Creating Board Game {} for board game store with ID={}", boardGameStoreBoardGame, boardGameStoreId);
		return boardGameStoreService.saveBoardGame(boardGameStoreId, boardGameStoreBoardGame);
	}
	
	@PostMapping("{boardGameStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BoardGameStoreCustomers insetCustomer(@PathVariable Long boardGameStoreId, @RequestBody BoardGameStoreCustomers boardGameStoreCustomers) {
		log.info("Creating customer {} for board game store with ID={}", boardGameStoreCustomers, boardGameStoreId);
		return boardGameStoreService.saveCustomer(boardGameStoreId, boardGameStoreCustomers);
	}
	
	@PostMapping("{boardGameId}/genere")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BoardGameGenere insertGenere(@PathVariable Long boardGameId, @RequestBody BoardGameGenere boardGameGenere) {
		log.info("Creating Genere {} for board game with ID={}", boardGameGenere, boardGameId);
		return boardGameStoreService.saveGenere(boardGameId, boardGameGenere);
	}
	
	@GetMapping("/{boardGameStoreId}")
	public BoardGameStoreData retriveBoardGameStoreById(@PathVariable Long boardGameStoreId) {
		log.info("Retrieving board game store by ID={}", boardGameStoreId);
		return boardGameStoreService.retrieveBoardGameStoreById(boardGameStoreId);
	}
	
	@DeleteMapping("/{boardGameStoreId}")
	public String deleteBoardGameStoreId(@PathVariable Long boardGameStoreId) {
		log.info("Deleting board game store with ID={}",boardGameStoreId);
		return boardGameStoreService.deleteBoardGameStoreId(boardGameStoreId);
		
		
	}

	
}
