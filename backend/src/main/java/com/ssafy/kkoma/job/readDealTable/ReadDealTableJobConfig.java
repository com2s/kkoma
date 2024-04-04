package com.ssafy.kkoma.job.readDealTable;

import com.ssafy.kkoma.api.notification.constant.NotiDetailBuilder;
import com.ssafy.kkoma.api.notification.dto.response.NotiDetail;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
import com.ssafy.kkoma.domain.notification.entity.Notification;
import com.ssafy.kkoma.domain.product.constant.MyProductType;
import com.ssafy.kkoma.job.JobLoggerListener;
import com.ssafy.kkoma.scheduler.DealReminderScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReadDealTableJobConfig {

    @Autowired
    DealReminderScheduler dealReminderJobScheduler;

    @Autowired
    private DealRepository dealRepository;

    @Bean
    public Job readDealTableJob(JobRepository jobRepository, Step readDealTableStep) {
        return new JobBuilder("readDealTableJob", jobRepository)
                .incrementer(new RunIdIncrementer()) // job 실행할 때 id 부여하는데 sequence 순차적으로 부여하기
                .listener(new JobLoggerListener())
                .start(readDealTableStep)
                .build();
    }

    @JobScope
    @Bean
    public Step readDealTableStep (
            JobRepository jobRepository,
            ItemReader readDealTableRepoReader,
            ItemProcessor readDealTableProcessor,
            ItemWriter readDealTableItemWriter,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("readDealTableStep", jobRepository)
                .<Deal, Notification>chunk(5, transactionManager) // 어떤 데이터로 읽어와서 어떤 데이터로 쓸건지, 5개의 사이즈만큼 데이터를 읽고 쓴 다음에 커밋하겠다
                .reader(readDealTableRepoReader)
                .processor(readDealTableProcessor)
                .writer(readDealTableItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<Deal> readDealTableRepoReader(
            @Value("#{jobParameters[lastRun]}") LocalDateTime lastRun,
            @Value("#{jobParameters[curRun]}") LocalDateTime curRun
    ) {
        log.info("itemReader에서... [lastRun] {} / [curRun] {}", lastRun, curRun);

        return new RepositoryItemReaderBuilder<Deal>()
                .name("readDealTableRepoReader")
                .repository(dealRepository)
                .methodName("findScheduledDealAfterLastRun")
                .pageSize(5) // 보통은 chunk size == page size
                .arguments(Arrays.asList(lastRun, curRun)) // method에 parameter 존재한다면, 지금은 없으니 빈 배열 넘김
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Deal, Notification> readDealTableProcessor() {
        return deal -> {
            LocalDateTime remindTime = deal.getSelectedTime().minusHours(1);
            Instant instant = remindTime.atZone(ZoneId.of("Asia/Seoul")).toInstant();
            log.info("deal {}는 {}에 알림 보내질 걸?", deal, remindTime);

            NotiDetail detailSeller = NotiDetailBuilder.getInstance().scheduledDeal(
                    deal.getProduct().getTitle(), MyProductType.SELL, deal.getProduct().getChatRoom().getId()
            );
            Notification notiSeller = Notification.builder()
                    .member(deal.getProduct().getMember())
                    .message(detailSeller.getMessage())
                    .destination(detailSeller.getDestination())
                    .sentAt(remindTime)
                    .build();

            dealReminderJobScheduler.scheduling(notiSeller, instant);

            NotiDetail detailBuyer = NotiDetailBuilder.getInstance().scheduledDeal(
                    deal.getProduct().getTitle(), MyProductType.BUY, deal.getProduct().getChatRoom().getId()
            );
            Notification notiBuyer = Notification.builder()
                    .member(deal.getMember())
                    .message(detailBuyer.getMessage())
                    .destination(detailBuyer.getDestination())
                    .sentAt(remindTime)
                    .build();

            dealReminderJobScheduler.scheduling(notiBuyer, instant);

            return notiBuyer;
        };
    }

    @StepScope
    @Bean
    public ItemWriter<Notification> readDealTableItemWriter() {
        return chunk -> log.info("처리 완료!");
    }

}
