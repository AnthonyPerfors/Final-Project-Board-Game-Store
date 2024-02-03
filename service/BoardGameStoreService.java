package board.game.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.game.store.controller.BoardGameStoreData;
import board.game.store.controller.BoardGameStoreData.BoardGameGenere;
import board.game.store.controller.BoardGameStoreData.BoardGameStoreBoardGame;
import board.game.store.controller.BoardGameStoreData.BoardGameStoreCustomers;
import board.game.store.dao.BoardGameDao;
import board.game.store.dao.BoardGameStoreDao;
import board.game.store.dao.CustomerDao;
import board.game.store.dao.GenereDao;
import board.game.store.entity.BoardGame;
import board.game.store.entity.BoardGameStore;
import board.game.store.entity.Customer;
import board.game.store.entity.Genere;

@Service
public class BoardGameStoreService {

	
	@Autowired
	private BoardGameStoreDao boardGameStoreDao;
	@Autowired
	private BoardGameDao boardGameDao;
	@Autowired
	private GenereDao genereDao;
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public BoardGameStoreData saveBoardGameStore(BoardGameStoreData boardGameStoreData) {
		Long boardGameStoreId = boardGameStoreData.getBoardGameStoreId();
		BoardGameStore boardGameStore = findOrCreateBoardGameStore(boardGameStoreId);
		copyBoardGameStoreFields(boardGameStore, boardGameStoreData);
		BoardGameStore dbBoardGameStore = boardGameStoreDao.save(boardGameStore);
		return new BoardGameStoreData(dbBoardGameStore);
	}

	public void copyBoardGameStoreFields(BoardGameStore boardGameStore, BoardGameStoreData boardGameStoreData) {

		boardGameStore.setBoardGameStoreId(boardGameStoreData.getBoardGameStoreId());
		boardGameStore.setBoardGameStoreName(boardGameStoreData.getBoardGameStoreName());
		boardGameStore.setBoardGameStoreCity(boardGameStoreData.getBoardGameStoreCity());
		boardGameStore.setBoardGameStoreState(boardGameStoreData.getBoardGameStoreState());
		boardGameStore.setBoardGameStoreNumber(boardGameStoreData.getBoardGameStoreNumber());
		
		
	}

	public BoardGameStore findOrCreateBoardGameStore(Long boardGameStoreId) {
		BoardGameStore boardGameStore;
		if(Objects.isNull(boardGameStoreId)) {
			boardGameStore = new BoardGameStore();
		} else {
			boardGameStore = findBoardGameStoreById(boardGameStoreId);
		}
		return boardGameStore;
	}

	public BoardGameStore findBoardGameStoreById(Long boardGameStoreId) {
		return boardGameStoreDao.findById(boardGameStoreId)
				.orElseThrow(() -> new NoSuchElementException("Board Game Store with ID=" + boardGameStoreId + " does not exist."));
	}
	
	@Transactional(readOnly = false)
	public BoardGameStoreBoardGame saveBoardGame(Long boardGameStoreID, BoardGameStoreBoardGame boardGameStoreBoardGame) {
		BoardGameStore boardGameStore = findBoardGameStoreById(boardGameStoreID);
		BoardGame boardGame = findOrCreateBoardGame(boardGameStoreID, boardGameStoreBoardGame.getBoardGameId());
		copyBoardGameFields(boardGame, boardGameStoreBoardGame);
		boardGame.getBoardGameStores().add(boardGameStore);
		boardGameStore.getBoardGames().add(boardGame);
		
		BoardGame dBoardGame = boardGameDao.save(boardGame);
		
		return new BoardGameStoreBoardGame(dBoardGame);
		
	}

	public void copyBoardGameFields(BoardGame boardGame, BoardGameStoreBoardGame boardGameStoreBoardGame) {
		boardGame.setBoardGameId(boardGameStoreBoardGame.getBoardGameId());
		boardGame.setBoardGameName(boardGameStoreBoardGame.getBoardGameName());
		boardGame.setMinPlayers(boardGameStoreBoardGame.getMinPlayers());
		boardGame.setMaxPlayers(boardGameStoreBoardGame.getMaxPlayers());
		boardGame.setDescription(boardGameStoreBoardGame.getDescription());
		
	}

	public BoardGame findOrCreateBoardGame(Long boardGameStoreID, Long boardGameId) {
		BoardGame boardGame;
		
		if(Objects.isNull(boardGameId)) {
			boardGame = new BoardGame();
		}else {
			boardGame = findBoardGameById(boardGameStoreID,boardGameId);
		}
		return boardGame;
	}

	public BoardGame findBoardGameById(Long boardGameStoreID, Long boardGameId) {
		boolean boardGameStoreIdsMatch = false;
		
		BoardGame boardGame = boardGameDao.findById(boardGameId)
				.orElseThrow(() -> new NoSuchElementException(
						"Board Game with ID=" + boardGameId + " does not exist."));
		
		Set<BoardGameStore> boardGameStores = boardGame.getBoardGameStores();
		for (BoardGameStore boardGameStore : boardGameStores ) {
			if (boardGameStore.getBoardGameStoreId() == boardGameStoreID) {
				boardGameStoreIdsMatch = true;
			}
		}
		
		if(boardGameStoreIdsMatch) {
			return boardGame;
		}else {
			throw new IllegalArgumentException("Board Game Store with ID=" + boardGameStoreID + " does not have a board game with ID=" + boardGameId);
		}				
	}
	
	@Transactional(readOnly = false)
	public BoardGameGenere saveGenere(Long boardGameId, BoardGameGenere boardGameGenere) {
		BoardGame boardGame = findBoardGameGenereById(boardGameId);
		Genere genere = findOrCreateGenere(boardGameId, boardGameGenere.getGenereId());
		copyGenereFields(genere, boardGameGenere);
		genere.setBoardGame(boardGame);
		boardGame.getGenere().add(genere);
		
		Genere dbGenere = genereDao.save(genere);
		
		return new BoardGameGenere(dbGenere);
	}

	private BoardGame findBoardGameGenereById(Long boardGameId) {
		return boardGameDao.findById(boardGameId)
				.orElseThrow(() -> new NoSuchElementException("Board Game with ID= " + boardGameId + " does not exist"));
	}

	public Genere findOrCreateGenere(Long boardGameId, Long genereId) {
		Genere genere;
		
		if(Objects.isNull(genereId)) {
			genere = new Genere();
		}else {
			genere = findGenereById(boardGameId, genereId);
		}
		return genere;
	}

	private Genere findGenereById(Long boardGameId, Long genereId) {
		Genere genere = genereDao.findById(genereId).orElseThrow(() -> new NoSuchElementException(
				"Genere with ID=" + genereId + " does not exist."));
		
		if(genere.getBoardGame().getBoardGameId() == boardGameId) {
			return genere;
		}else {
			throw new IllegalArgumentException("Board game with ID=" + boardGameId + " does not not have a genere with ID=" + genereId);
		}
	}

	public void copyGenereFields(Genere genere, BoardGameGenere boardGameGenere) {
		genere.setGenereId(boardGameGenere.getGenereId());
		genere.setCategory(boardGameGenere.getCategory());
		
	}
	
	@Transactional(readOnly = false)
	public BoardGameStoreCustomers saveCustomer(long boardGameStoreId, BoardGameStoreCustomers boardGameStoreCustomer) {
	
		Customer customer = findOrCreateCustomer(boardGameStoreCustomer.getCustomerID());
		copyCustomerFields(customer, boardGameStoreCustomer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new BoardGameStoreCustomers(dbCustomer);
	}

	private Customer findOrCreateCustomer(Long customerID) {
		Customer customer;
		
		if(Objects.isNull(customerID)) {
			customer = new Customer();
		}else {
			customer = findCustomerById(customerID);
		}
		return customer;
	}

	private Customer findCustomerById(Long customerID) {
		Customer customer = customerDao.findById(customerID)
				.orElseThrow(() -> new NoSuchElementException(
						"Customer with ID=" + customerID + " does not exist."));
		
		if(customer.getCustomerID() == customerID) {
			return customer;
		}else {
			throw new IllegalArgumentException("Customer with ID=" + customerID + " does not exist.");
		}
	}

	private void copyCustomerFields(Customer customer, BoardGameStoreCustomers boardGameStoreCustomer) {
		customer.setCustomerID(boardGameStoreCustomer.getCustomerID());
		customer.setCustomerFirstName(boardGameStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(boardGameStoreCustomer.getCustomerLastName());
		customer.setCustomerPhoneNumber(boardGameStoreCustomer.getCustomerPhoneNumber());
		customer.setCustomerEmail(boardGameStoreCustomer.getCustomerEmail());
		
	}

	public BoardGameStoreData retrieveBoardGameStoreById(long boardGameStoreId) {
		BoardGameStore boardGameStore = findBoardGameStoreById(boardGameStoreId);
		return new BoardGameStoreData(boardGameStore);
	}

	public String deleteBoardGameStoreId(long boardGameStoreId) {
		
		BoardGameStore boardGameStore = findBoardGameStoreById(boardGameStoreId);
		
		boardGameStoreDao.delete(boardGameStore);
		return "boardGameId= " + boardGameStoreId + " Deleted";
			
		
	}
	                                                    
	
}
