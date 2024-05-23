package springboot.project.config;

import lombok.RequiredArgsConstructor;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import springboot.project.component.AutowiringSpringBeanJobFactory;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {
    private final AutowireCapableBeanFactory beanFactory;

    @Bean
    public AutowiringSpringBeanJobFactory jobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory(beanFactory);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(AutowiringSpringBeanJobFactory jobFactory) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        return factory;
    }
}