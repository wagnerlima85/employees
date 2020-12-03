package br.com.wagnerlima85.employees.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",length = 255)
    private String name;

    @Column(name = "min_maturity", nullable = false)
    private Integer minMaturity;

    @Column(name = "curr_maturity", nullable = false)
    private Integer currMaturity;

    @PrePersist
	public void persistTimeStamps() {
		this.currMaturity = 0;
	}

}
