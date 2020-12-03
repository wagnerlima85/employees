package br.com.wagnerlima85.employees.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.wagnerlima85.employees.model.Team;
import br.com.wagnerlima85.employees.model.Employee;
import br.com.wagnerlima85.employees.repository.TeamRepository;
import br.com.wagnerlima85.employees.utils.EmployeeUtils;
import br.com.wagnerlima85.employees.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamRepository teamRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public void loadCSV() {
        var employees = EmployeeUtils.readCSV("employees.csv");
        var teams = EmployeeUtils.readCSV("teams.csv");

        employeeRepository.deleteAll();
        teamRepository.deleteAll();

        teams.forEach((items) ->{
            var c = new Team();
            c.setName(items.get(0));
            c.setMinMaturity(Integer.parseInt(items.get(1)));
            teamRepository.save(c);
        });

        employees.forEach((items) ->{
            var e = new Employee();
            e.setName(items.get(0));
            e.setLevel(Integer.parseInt(items.get(1)));
            e.setBirthYear(EmployeeUtils.convertToLocalDate(items.get(2)));
            e.setAdmissionYear(EmployeeUtils.convertToLocalDate(items.get(3)));
            e.setLastProgressionYear(EmployeeUtils.convertToLocalDate(items.get(4)));
            e.setWeight(EmployeeUtils.getWeight(e));
            employeeRepository.save(e);
        });

        logger.info("Loaded");
    }

    public void promote(Integer size) {
        var employees = employeeRepository.listOrderByWeight();

        for (Employee e : employees) {
            if (e.getLevel() > 4
                    || (e.getLevel() < 4 && ChronoUnit.YEARS.between(e.getLastProgressionYear(), LocalDate.now()) < 1)
                    || (e.getLevel() == 4 && ChronoUnit.YEARS.between(e.getLastProgressionYear(), LocalDate.now()) < 2))
                continue;

            if (size == 0)
                break;

            e.setLevel(e.getLevel() + 1);
            e.setLastProgressionYear(EmployeeUtils.convertToLocalDate(Integer.toString(Calendar.getInstance().get(Calendar.YEAR))));
            e.setWeight(EmployeeUtils.getWeight(e));
            employeeRepository.save(e);
            logger.info("Name: " + e.getName() + " - From: " + (e.getLevel() - 1) + "- To: " + e.getLevel());
            size--;
        }
    }

    
}
