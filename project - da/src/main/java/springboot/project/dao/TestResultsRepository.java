package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springboot.project.entity.TestResults;

import java.util.List;

public interface TestResultsRepository extends JpaRepository<TestResults, Integer> {
    @Query(value = "select tr.* from test_results tr\n" +
            "join patients p on \n" +
            "p.id = tr.patient_id \n" +
            "join users u \n" +
            "on u.id = p.patient_id\n" +
            "where u.id = ?",nativeQuery = true)
    List<TestResults> findResult(int id);
}