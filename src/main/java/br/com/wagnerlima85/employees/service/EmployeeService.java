package br.com.wagnerlima85.employees.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import br.com.wagnerlima85.employees.model.Team;
import br.com.wagnerlima85.employees.model.Employee;
import br.com.wagnerlima85.employees.repository.TeamRepository;
import br.com.wagnerlima85.employees.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamRepository teamRepository;

    public void loadCSV() {
        // Mapeando as planilhas
        var employees = this.readCSV("employees.csv");
        var teams = this.readCSV("teams.csv");

        // Limpando os registros existentes para a execução do novo exercício
        employeeRepository.deleteAll();
        teamRepository.deleteAll();

        // Rgistrando os times antes de criar funcionários
        for (List<String> items : teams) {
            var c = new Team();
            c.setName(items.get(0));
            c.setMinMaturity(Integer.parseInt(items.get(1)));
            teamRepository.save(c);
        }

        // Registrando os usuários sem alocalos, inicialmente a nenhum time
        for (List<String> items : employees) {
            var e = new Employee();
            e.setName(items.get(0));
            e.setLevel(Integer.parseInt(items.get(1)));
            e.setBirthYear(this.convertToLocalDate(items.get(2)));
            e.setAdmissionYear(this.convertToLocalDate(items.get(3)));
            e.setLastProgressionYear(this.convertToLocalDate(items.get(4)));
            e.setWeight(this.getWeight(e));
            employeeRepository.save(e);
        }
        // Finalizando o processo
        System.out.println("Loaded");

    }

    public void allocateOrBalance(boolean isBalance) {

        // Iniciando o processo de alocação carregando usuários e times
        // utilizando recursos nativos do spring
        var teams = (List<Team>) teamRepository.findAll();
        //Estruturas balenceada será ordenada pelo peso
        var employees = isBalance ? (List<Employee>) employeeRepository.listOrderByWeight() : (List<Employee>) employeeRepository.findAll();
        // Variável que serve para controlar em qual
        var team = 0;
        // Para seguir a estrutura inicial sugerida, foi necessário
        Collections.reverse(employees);

        // Redefinindo a maturidade do time para atualização
        for (Team t : teams)
            t.setCurrMaturity(0);

        // Atualizando os funcionários com a vinculação ao seu respectivo time
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

        // Atualizando todos os times
        teamRepository.saveAll(teams);

        // Imprimindo informações no console
        for (Team t : teams) {
            System.out.println(t.getName() + " - Min. Maturity: " + t.getMinMaturity() + " - Current Maturity: "
                    + t.getCurrMaturity());
            for (Employee e : employees) {
                if (e.getTeam().getId().equals(t.getId()))
                    System.out.println("Name: " + e.getName() + " - Level: " + e.getLevel());
            }
        }
    }

    public void promote(Integer size) {

        // Ordenando lista de usuários baseado em critérios somatórios para priorização
        // em promoções
        var employees = employeeRepository.listOrderByWeight();

        for (Employee e : employees) {
            // Configurando validador para atualizar apenas funcionários que se encaixarem
            // nos critérios pré-definidos
            if (e.getLevel() > 4
                    || (e.getLevel() < 4 && ChronoUnit.YEARS.between(e.getLastProgressionYear(), LocalDate.now()) < 1)
                    || (e.getLevel() == 4 && ChronoUnit.YEARS.between(e.getLastProgressionYear(), LocalDate.now()) < 2))
                continue;

            if (size == 0)
                break;

            //Atualizando informaçẽos de usuários sobre sua promoção e ajustando seu 'peso' para promoções futuras
            e.setLevel(e.getLevel() + 1);
            e.setLastProgressionYear(this.convertToLocalDate(Integer.toString(Calendar.getInstance().get(Calendar.YEAR))));
            e.setWeight(this.getWeight(e));
            employeeRepository.save(e);
            // Imprimindo resultado do colaborador promovido
            System.out.println("Name: " + e.getName() + " - From: " + (e.getLevel() - 1) + "- To: " + e.getLevel());
            //Atualizando a quntidade de funcionários pendentes de atualização
            size--;
        }
    }

    private List<List<String>> readCSV(String fileName) {

        //Mecanismo genérico para leitura de planilhas
        var list = new ArrayList<List<String>>();
        try {
            var f = ResourceUtils.getFile("classpath:" + fileName);
            var br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            var line = "";
            while ((line = br.readLine()) != null) {
                var values = (line.split(",").length >0) ? line.split(",") : line.split(";");
                list.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private LocalDate convertToLocalDate(String year) {
        //Conversão do ano no formato de LocalDate
        var formatter = new DateTimeFormatterBuilder().appendPattern("yyyy")
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1).parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

        return LocalDate.parse(year, formatter);
    }

    private Integer getWeight(Employee e) {
        //Definindo critérios para definir a pontuação para promoção de funcionários
        var born = ChronoUnit.YEARS.between(e.getBirthYear(), LocalDate.now()) / 5;
        var progression = ChronoUnit.YEARS.between(e.getLastProgressionYear(), LocalDate.now()) * 3;
        var admission = ChronoUnit.YEARS.between(e.getAdmissionYear(), LocalDate.now()) * 2;

        return (int) (born + progression + admission);
    }
}
