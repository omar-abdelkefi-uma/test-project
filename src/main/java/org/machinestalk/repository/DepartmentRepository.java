package org.machinestalk.repository;

import java.util.Optional;

import org.machinestalk.domain.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
	Optional<Department> findByName(String name);

}
