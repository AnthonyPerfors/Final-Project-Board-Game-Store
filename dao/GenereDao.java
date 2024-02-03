package board.game.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import board.game.store.entity.Genere;

public interface GenereDao extends JpaRepository<Genere, Long> {

}
