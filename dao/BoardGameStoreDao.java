package board.game.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import board.game.store.entity.BoardGameStore;

public interface BoardGameStoreDao extends JpaRepository<BoardGameStore, Long> {

}
