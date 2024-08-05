package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.UpdateRequest;
@Repository
public interface UpdateRequestRepository extends JpaRepository<UpdateRequest, Long>
{
	Optional<UpdateRequest> findByUid(long uid);
	List<UpdateRequest> findByUsername(String username);
}
