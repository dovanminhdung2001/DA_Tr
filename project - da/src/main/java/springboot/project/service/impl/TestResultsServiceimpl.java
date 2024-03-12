package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.ScheduleRepository;
import springboot.project.dao.TestResultsRepository;
import springboot.project.entity.Schedule;
import springboot.project.entity.TestResults;
import springboot.project.model.TestResultsDTO;
import springboot.project.service.TestResultsService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TestResultsServiceimpl implements TestResultsService {
    @Autowired
    TestResultsRepository testResultsRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Override
    public TestResults viewResult(int id) {
        TestResults testResults = testResultsRepository.findById(id).orElse(null);
        return testResults;
    }

    @Override
    public List<TestResultsDTO> getListResults(int id) {
        List<TestResults> testResults = testResultsRepository.findResult(id);
        List<TestResultsDTO> testResultsDTOS = new ArrayList<>();
        for (TestResults testResults1 : testResults) {
            testResultsDTOS.add(convert(testResults1));
        }
        return testResultsDTOS;
    }

    @Override
    public TestResults addResult(TestResultsDTO testResultsDTO) {
        TestResults testResults = new TestResults();
        Schedule schedule = scheduleRepository.findById(testResultsDTO.getScheduleId()).get();

//        testResults.setId(testResultsDTO.getId());
        testResults.setUserId(testResultsDTO.getUserId());
        testResults.setDescription(testResultsDTO.getDescription());
        testResults.setSchedule(schedule);
//        testResults.setPatient(testResultsDTO.getPatient());
        testResultsRepository.save(testResults);
        return testResults;
    }

    private TestResultsDTO convert(TestResults testResults1) {
        TestResultsDTO testResultsDTO = new TestResultsDTO();
        testResultsDTO.setId(testResults1.getId());
        testResultsDTO.setDescription(testResults1.getDescription());
        testResultsDTO.setSchedule(testResults1.getSchedule());
        testResultsDTO.setUser(testResults1.getPatient().getUser());
        return testResultsDTO;
    }


}
