package br.com.wagnerlima85.employees.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.wagnerlima85.employees.model.Team;


public interface TeamRepository extends CrudRepository<Team, Long> {
    
}
