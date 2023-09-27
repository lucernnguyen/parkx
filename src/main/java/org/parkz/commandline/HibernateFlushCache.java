package org.parkz.commandline;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.jpa.properties.hibernate.cache", name = "flush_on_boot", havingValue = "true")
public class HibernateFlushCache implements CommandLineRunner {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public void run(String... args) {
        log.info("Flushing hibernate cache");
        entityManagerFactory.getCache().evictAll();
    }
}
