package board.game.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import board.game.store.entity.BoardGame;

public interface BoardGameDao extends JpaRepository<BoardGame, Long> {

}
