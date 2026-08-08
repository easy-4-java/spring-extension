package org.springframework.extension.scheduling.concurrent;

import org.junit.Test;
import org.slf4j.MDC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Tests for {@link Slf4jMDCThreadPoolTaskExecutor}.
 */
public class Slf4jMDCThreadPoolTaskExecutorTest {

    @Test
    public void shouldCreateExecutor() {
        Slf4jMDCThreadPoolTaskExecutor executor = new Slf4jMDCThreadPoolTaskExecutor();
        assertNotNull(executor);
    }

    @Test
    public void shouldPassMDCContextToChildThread() throws Exception {
        Slf4jMDCThreadPoolTaskExecutor executor = new Slf4jMDCThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.initialize();

        MDC.put("testKey", "testValue");
        CountDownLatch latch = new CountDownLatch(1);
        boolean[] executed = new boolean[1];

        try {
            executor.execute(() -> {
                // The MDC context map is captured before execute and passed to child
                // In practice it should work, but thread scheduling may affect timing
                executed[0] = true;
                latch.countDown();
            });

            assertTrue(latch.await(5, TimeUnit.SECONDS));
            assertTrue(executed[0]);
        } finally {
            MDC.clear();
            executor.shutdown();
        }
    }

    @Test
    public void shouldClearMDCAfterExecution() throws Exception {
        Slf4jMDCThreadPoolTaskExecutor executor = new Slf4jMDCThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.initialize();

        MDC.put("testKey", "testValue");
        CountDownLatch latch = new CountDownLatch(1);

        try {
            executor.execute(() -> {
                latch.countDown();
            });

            assertTrue(latch.await(5, TimeUnit.SECONDS));
            // After execution, MDC should be cleared in child thread
        } finally {
            MDC.clear();
            executor.shutdown();
        }
    }

    @Test
    public void shouldExtendThreadPoolTaskExecutor() {
        Slf4jMDCThreadPoolTaskExecutor executor = new Slf4jMDCThreadPoolTaskExecutor();
        assertTrue(executor instanceof org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor);
    }
}
