package springboot.project.service;

import springboot.project.entity.TestResults;
import springboot.project.model.TestResultsDTO;

import java.util.List;

public interface TestResultsService {
    TestResults viewResult(int id);
    List<TestResultsDTO> getListResults(int id);
    TestResults addResultEmployee(TestResultsDTO testResultsDTO);
    TestResults addResultDoctor(TestResultsDTO testResultsDTO);
}
