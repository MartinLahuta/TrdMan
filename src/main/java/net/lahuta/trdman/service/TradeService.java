package net.lahuta.trdman.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import net.lahuta.trdman.dto.PositionDto;
import net.lahuta.trdman.dto.TradeDto;
import net.lahuta.trdman.entity.Position;
import net.lahuta.trdman.entity.Trade;
import net.lahuta.trdman.mapper.PositionMapper;
import net.lahuta.trdman.netting.NettingModel;
import net.lahuta.trdman.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.lahuta.trdman.repository.TradeRepository;
import net.lahuta.trdman.mapper.TradeMapper;

@Slf4j
@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private NettingModel nettingModel;
    
    // tady nemusi byt @Transactional pokud se pouzije findAllFetchOrderedTrades
    // (nepouziva se proxy)
    @Transactional(readOnly = true)
    public List<TradeDto> findAllTrades() {
        return tradeMapper.tradesToTradesDtos(tradeRepository.findAll());
//        return tradeMapper.tradeToTradeDtos(itemRepository.findAllFetchOrderedItems())
    }

    @Transactional(readOnly = true)
    public List<PositionDto> findAllPositions() {
        return positionMapper.positionsToPositionDtos(positionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public long countTrades() {
        return tradeRepository.countTrades();
    }

    @Transactional(readOnly = true)
    public long countPositions() {
        return positionRepository.countPositions();
    }

    // Hibernate
    @Transactional(readOnly = true)
    public Optional<TradeDto> findTradeById(long id) {
        Optional<Trade> trd = tradeRepository.findById(id);
        return tradeRepository.findById(id)
                .map(tradeMapper::tradeToTradeDto);
    //    return tradeRepository.findById(id); - hibernate bez dto
    }

    @Transactional
    public void deleteById(long id) {
        tradeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PositionDto> findPositionById(long id) {
        Optional<Position> position = positionRepository.findById(id);
        return positionRepository.findById(id).map(positionMapper::positionToPositionDto);
    }

    @Transactional
    public void netTrades(String key) {
        log.info("Netting: key={}", key);

        List<Trade> trades = tradeRepository.findAllByMember(key);

        if(trades.isEmpty()) {
            log.info("Empty result.");
            return;
        }

        positionRepository.saveAll(nettingModel.doNetting(key, trades));

    }

}