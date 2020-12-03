package br.com.wagnerlima85.employees;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.wagnerlima85.employees.model.Employee;
import br.com.wagnerlima85.employees.model.Team;
import br.com.wagnerlima85.employees.repository.EmployeeRepository;
import br.com.wagnerlima85.employees.repository.TeamRepository;

import org.junit.Assert;


@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(properties = {
	InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})class EmployeesApplicationTests {


	@MockBean
	private EmployeeRepository mockEmployeeRepository;
	
	private EmployeeRepository repoEmployee;

	@MockBean
	private TeamRepository mockTeamRepository;
	
	private TeamRepository repoTeam;
	
	@Autowired
	private ApplicationContext context;
	
	@Test
	void testCreateEmployee(){
		var item = new Employee();
		item.setId(1);
		item.setName("José Antônio");
		item.setLevel(2);
		item.setAdmissionYear(LocalDate.now());
		item.setBirthYear(LocalDate.now());
		item.setLastProgressionYear(LocalDate.now());
		item.setWeight(1);

		Mockito.when(mockEmployeeRepository.save(item)).thenReturn(item);
		
		repoEmployee = context.getBean(EmployeeRepository.class);
		repoEmployee.save(item);
        Assert.assertEquals("José Antônio", item.getName());
		Assert.assertNotEquals("Jose Antonio", item.getName());
		
        Mockito.verify(mockEmployeeRepository).save(item);
	}

	@Test
	void testSaveAllEmployee(){

		var item = new Employee();
		item.setId(1);
		item.setName("José Antônio");
		item.setLevel(2);
		item.setAdmissionYear(LocalDate.now());
		item.setBirthYear(LocalDate.now());
		item.setLastProgressionYear(LocalDate.now());
		item.setWeight(1);

		var item2 = new Employee();
		item2.setId(2);
		item2.setName("José Carlos");
		item2.setLevel(2);
		item2.setAdmissionYear(LocalDate.now());
		item2.setBirthYear(LocalDate.now());
		item2.setLastProgressionYear(LocalDate.now());
		item2.setWeight(2);

		var asList = Arrays.asList(item, item2);
		Mockito.when(mockEmployeeRepository.saveAll(asList)).thenReturn(asList);
		
		repoEmployee = context.getBean(EmployeeRepository.class);
		repoEmployee.saveAll(asList);
        Assert.assertEquals(asList, asList);
        Mockito.verify(mockEmployeeRepository).saveAll(asList);
	}

	@Test
	void testCreateTeam(){
		var item = new Team();
		item.setCurrMaturity(3);
		item.setName("Time Original");
		item.setMinMaturity(1);

		Mockito.when(mockTeamRepository.save(item)).thenReturn(item);
		
		repoTeam = context.getBean(TeamRepository.class);
		repoTeam.save(item);
        Assert.assertEquals("Time Original", item.getName());
		Assert.assertNotEquals("Time Grande", item.getName());
		
        Mockito.verify(mockTeamRepository).save(item);
	}

	@Test
	void testSaveAllTeam(){
		var item = new Team();
		item.setCurrMaturity(3);
		item.setName("Time Original");
		item.setMinMaturity(1);

		var item2 = new Team();
		item2.setCurrMaturity(6);
		item2.setName("Time Grande");
		item2.setMinMaturity(4);

		var asList = Arrays.asList(item, item2);
		Mockito.when(mockTeamRepository.saveAll(asList)).thenReturn(asList);
		
		repoTeam = context.getBean(TeamRepository.class);
		repoTeam.saveAll(asList);
        Assert.assertEquals(asList, asList);
        Mockito.verify(mockTeamRepository).saveAll(asList);
	}

}
