package com.sulwork.cafe.scheduler;

import com.sulwork.cafe.service.TrazerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoMarkScheduler {
  private static final Logger log = LoggerFactory.getLogger(AutoMarkScheduler.class);
  private final TrazerService service;

  @Scheduled(cron = "0 5 0 * * *", zone = "America/Sao_Paulo")
  public void marcarPendentesComoNaoTrouxe() {
    int updated = service.autoMarcarPendentesComoNaoTrouxe();
    log.info("AutoMarkScheduler: {} registros pendentes marcados como FALSE", updated);
  }
}
