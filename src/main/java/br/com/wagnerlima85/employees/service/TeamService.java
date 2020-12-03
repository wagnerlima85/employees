package br.com.wagnerlima85.employees.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnerlima85.employees.model.Team;
import br.com.wagnerlima85.employees.model.Employee;
import br.com.wagnerlima85.employees.repository.TeamRepository;
import br.com.wagnerlima85.employees.repository.EmployeeRepository;

@Service
public class TeamService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamRepository teamRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public void allocateOrBalance(boolean isBalance) {

        var teams = (List<Team>) teamRepository.findAll();
        var employees = isBalance ? (List<Employee>) employeeRepository.listOrderByWeight()
                : (List<Employee>) employeeRepository.findAll();
        var team = 0;
        Collections.reverse(employees);

        for (Team t : teams)
            t.setCurrMaturity(0);

        for (var i = 0; i < employees.size(); i++) {
            if (i > 4)
                team = 2;
            else if (i > 2)
                team = 1;

            var e = employees.get(i);
            e.setTeam(teams.get(team));
            employeeRepository.save(e);

            var currMaturity = teams.get(team).getCurrMaturity();
            teams.get(team).setCurrMaturity(currMaturity + e.getLevel());
        }

        teamRepository.saveAll(teams);

        teams.forEach((t) -> {
            logger.info(t.getName() + " - Min. Maturity: " + t.getMinMaturity() + " - Current Maturity: "
                    + t.getCurrMaturity());
            for (Employee e : employees) {
                if (e.getTeam().getId().equals(t.getId()))
                    logger.info("Name: " + e.getName() + " - Level: " + e.getLevel());
            }
        });
    }
}
