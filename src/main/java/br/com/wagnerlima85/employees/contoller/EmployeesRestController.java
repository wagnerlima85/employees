package br.com.wagnerlima85.employees.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wagnerlima85.employees.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeesRestController {
  
    @Autowired
    private EmployeeService service;

    @PostMapping(value = "/load")
    public ResponseEntity<?> load(){
        service.loadCSV();
        return ResponseEntity.ok().body("Comando executado com sucesso. Vefiriquei o log para mais informações");
    }

    @PutMapping(value = "/promote/{value}")
    public ResponseEntity<?> promote(@PathVariable String value){
        service.promote(Integer.parseInt(value));
        return ResponseEntity.ok().body("Comando executado com sucesso. Vefiriquei o log para mais informações");
    }

    @PutMapping(value = "/allocate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allocate(){
        service.allocateOrBalance(false);
        return ResponseEntity.ok().body("Comando executado com sucesso. Vefiriquei o log para mais informações");
    }

    @PutMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> balance(){
        service.allocateOrBalance(true);
        return ResponseEntity.ok().body("Comando executado com sucesso. Vefiriquei o log para mais informações");
    }
}
