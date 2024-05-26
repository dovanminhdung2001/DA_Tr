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
import springboot.project.utils.Const;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TestResultsServiceImpl implements TestResultsService {
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
    public TestResults addResultEmployee(TestResultsDTO testResultsDTO) {
        TestResults testResults = new TestResults();
        Schedule schedule = scheduleRepository.findById(testResultsDTO.getScheduleId()).get();

        schedule.setEmployeeId(testResultsDTO.getEmployeeId());
        schedule = scheduleRepository.save(schedule);
        testResults.setUserId(testResultsDTO.getUserId());
        testResults.setDescription(testResultsDTO.getDescription());
        testResults.setSchedule(schedule);
        testResults.setStatus(Const.TEST_RESULTED_BY_EMPLOYEE);
        testResults = testResultsRepository.save(testResults);
        return testResults;
    }

    @Override
    public TestResults addResultDoctor(TestResultsDTO testResultsDTO) {
        TestResults testResults = testResultsRepository.findById(testResultsDTO.getId()).get();

        if (testResults == null)
            throw new RuntimeException("not existed id");

        Schedule schedule = testResults.getSchedule();

        testResults.setNote(testResultsDTO.getNote());
        testResults.setStatus(Const.TEST_RESULTED_BY_DOCTOR);
        schedule.setStatus(Const.SCHEDULE_STATUS_RESULTED);

        scheduleRepository.save(schedule);
        testResults = testResultsRepository.save(testResults);

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
