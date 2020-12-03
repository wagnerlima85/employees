package br.com.wagnerlima85.employees.utils;

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
import java.util.List;

import org.springframework.util.ResourceUtils;

import br.com.wagnerlima85.employees.model.Employee;

public class EmployeeUtils {

    public static List<List<String>> readCSV(String fileName) {

        var list = new ArrayList<List<String>>();
        try {
            var f = ResourceUtils.getFile("classpath:" + fileName);
            var br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            var line = "";
            while ((line = br.readLine()) != null) {
                var values = (line.split(",").length > 0) ? line.split(",") : line.split(";");
                list.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static LocalDate convertToLocalDate(String year) {

        var formatter = new DateTimeFormatterBuilder().appendPattern("yyyy")
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1).parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

        return LocalDate.parse(year, formatter);
    }

    public static int getWeight(Employee e) {
        
        var born = ChronoUnit.YEARS.between(e.getBirthYear(), LocalDate.now()) / 5;
        var progression = ChronoUnit.YEARS.between(e.getLastProgressionYear(), LocalDate.now()) * 3;
        var admission = ChronoUnit.YEARS.between(e.getAdmissionYear(), LocalDate.now()) * 2;

        return (int) (born + progression + admission);
    }

}
