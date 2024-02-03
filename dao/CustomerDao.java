package board.game.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import board.game.store.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
