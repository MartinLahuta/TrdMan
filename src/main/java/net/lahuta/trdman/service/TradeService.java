package net.lahuta.trdman.service;

import java.util.List;
import java.util.Optional;
import net.lahuta.trdman.dto.TradeDto;
import net.lahuta.trdman.entity.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.lahuta.trdman.repository.TradeRepository;
import net.lahuta.trdman.mapper.TradeMapper;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TradeMapper tradeMapper;
    
    // tady nemusi byt @Transactional pokud se pouzije findAllFetchOrderedTrades
    // (nepouziva se proxy)
    @Transactional(readOnly = true)
    public List<TradeDto> findAll() {
        return tradeMapper.tradesToTradesDtos(tradeRepository.findAll());
//        return tradeMapper.tradeToTradeDtos(itemRepository.findAllFetchOrderedItems())
    }

    @Transactional(readOnly = true)
    public long countItems() {
        return tradeRepository.countItems();
    }
    
    // Hibernate
    @Transactional(readOnly = true)
    public Optional<TradeDto> findById(long id) {
        Optional<Trade> trd = tradeRepository.findById(id);
        return tradeRepository.findById(id)
                .map(tradeMapper::tradeToTradeDto);
    //    return tradeRepository.findById(id); - hibernate bez dto
    }

}