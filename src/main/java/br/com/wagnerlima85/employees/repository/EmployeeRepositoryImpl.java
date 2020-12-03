package br.com.wagnerlima85.employees.repository;

import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.wagnerlima85.employees.model.Employee;

@Repository
@Transactional(readOnly = true)
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    private EntityManager em;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        em = entityManager;
    }

    @Override
    public List<Employee> listOrderByWeight() {

        var builder = em.getCriteriaBuilder();
        var criteria = builder.createQuery(Employee.class);
        var root = criteria.from(Employee.class);
        criteria.select(root);
        criteria.orderBy(builder.desc(root.get("weight")));

        return em.createQuery(criteria).getResultList();
    }

}
