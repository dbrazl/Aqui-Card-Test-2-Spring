package com.aquicard.src.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aquicard.src.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u from User u order by id")
	List<User> findAllAndSort();
	
	@Query("select u from User u where u.name like %?1%")
	List<User> findAllHasName(String name);
}
