package com.dinein.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dinein.model.Staff;

@Repository
public interface StaffRepository extends CrudRepository<Staff, String>{

	Optional<Staff> findTopByOrderByIdDesc();

	Optional<Staff> findByEmailIdAndPassword(String emailId, String password);

	Optional<Staff> findByEmailId(String emailId);

	List<Staff> findAllByDesignation(String string);

	Optional<Staff> findByEmployeeId(String string);

}
