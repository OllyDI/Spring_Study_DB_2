package hello.springtx.apply;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class InitTxTest {

    @Autowired Hello hello;

    @Test
    void go() {
        // 초기화 코드는 스프링 초기화 시점에 호출
    }

    @TestConfiguration
    static class InitTxTestConfig {

        @Bean
        Hello hello() {
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {

        // 스프링 초기화 시점에 자동 호출 -> 트랜잭션 적용X, 직접 호출하면 트랜잭션 적용O
        @PostConstruct
        @Transactional
        public void initV1() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @PostConstruct tx Active={}", isActive);
        }

        // 초기화 시점에 트랜잭션 적용 예제 -> 스프링 컨테이너가 전부
        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initV2() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @EventListener tx Active={}", isActive);
        }
    }
}
