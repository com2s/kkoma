package com.ssafy.kkoma.job.dealReminder;

import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import com.ssafy.kkoma.domain.notification.repository.NotificationRepository;
import com.ssafy.kkoma.job.JobLoggerListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DealReminderJobConfig {

    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Bean
    public Job dealReminderJob(JobRepository jobRepository, Step dealReminderStep) {
        return new JobBuilder("dealReminderJob", jobRepository)
                .incrementer(new RunIdIncrementer()) // job 실행할 때 id 부여하는데 sequence 순차적으로 부여하기
                .listener(new JobLoggerListener())
                .start(dealReminderStep)
                .build();
    }

    @JobScope
    @Bean
    public Step dealReminderStep (
            JobRepository jobRepository,
            ItemReader dealReminderRepoReader,
            ItemProcessor dealReminderProcessor,
            ItemWriter dealReminderItemWriter,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("dealReminderStep", jobRepository)
                .<Deal, Notification>chunk(5, transactionManager) // 어떤 데이터로 읽어와서 어떤 데이터로 쓸건지, 5개의 사이즈만큼 데이터를 읽고 쓴 다음에 커밋하겠다
                .reader(dealReminderRepoReader)
                .processor(dealReminderProcessor)
                .writer(dealReminderItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<Deal> dealReminderRepoReader() {
        return new RepositoryItemReaderBuilder<Deal>()
                .name("dealReminderRepoReader")
                .repository(dealRepository)
                .methodName("findScheduledDeal")
                .pageSize(5) // 보통은 chunk size == page size
                .arguments(Arrays.asList(LocalDateTime.now())) // method에 parameter 존재한다면, 지금은 없으니 빈 배열 넘김
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Deal, List<Notification>> dealReminderProcessor() {
        return new ItemProcessor<Deal, List<Notification>>() {
            @Override
            public List<Notification> process(Deal deal) throws Exception {
                List<Notification> notiList = new ArrayList<>();

                notiList.add(Notification.builder()
                    .member(deal.getProduct().getMember())
                    .message("1시간 후 '" + deal.getProduct().getTitle() + "' 판매 약속이 예정되어 있습니다!")
                    .destination("todo")
                    .sentAt(LocalDateTime.now())
                    .build()
                );

                notiList.add(Notification.builder()
                    .member(deal.getMember())
                    .message("1시간 후 '" + deal.getProduct().getTitle() + "' 구매 약속이 예정되어 있습니다!")
                    .destination("todo")
                    .sentAt(LocalDateTime.now())
                    .build()
                );

                return notiList;
            }
        };
    }

    @StepScope
    @Bean
    public ItemWriter<List<Notification>> dealReminderItemWriter() {
        return new ItemWriter<List<Notification>>() {
            @Override
            public void write(Chunk<? extends List<Notification>> chunk) throws Exception {
                chunk.forEach( notiList -> {
                    for (Notification noti : notiList) {
                        notificationRepository.save(noti);
                    }
                });
            }
        };
    }

}
