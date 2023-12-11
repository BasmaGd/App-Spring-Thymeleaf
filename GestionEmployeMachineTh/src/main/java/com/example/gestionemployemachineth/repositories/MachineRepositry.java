package com.example.gestionemployemachineth.repositories;

import com.example.gestionemployemachineth.entities.Employee;
import com.example.gestionemployemachineth.entities.Machine;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MachineRepositry extends CrudRepository<Machine, Long> {
	 List<Machine> findAllByOrderByDateAchatAsc();
	    //List<Machine> findByEmployeid(Employee employee);
	    List<Machine> findByEmployeid(Employee employee);

}
