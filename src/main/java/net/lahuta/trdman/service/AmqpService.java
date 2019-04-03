package net.lahuta.trdman.service;


import lombok.extern.slf4j.Slf4j;
import net.lahuta.trdman.entity.Trade;
import net.lahuta.trdman.exceptions.TradeParseException;
import net.lahuta.trdman.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AmqpService {

    @Autowired
    private TradeParser tradeParser;

    @Autowired
    private TradeRepository tradeRepository;

//    @Async("threadPoolTaskExecutor")
    public void receiveMessage(String xml) {
        try {
            Trade trade = tradeParser.parseXMLTrade(xml);
            tradeRepository.save(trade);
        } catch(TradeParseException e) {
            log.info("Invalid trade:\n{}", xml);
        }
    }

}