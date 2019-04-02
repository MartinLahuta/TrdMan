package net.lahuta.trdman.controller;

import java.util.List;
import java.util.Optional;
import net.lahuta.trdman.dto.TradeDto;
import net.lahuta.trdman.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    // http://localhost:8080/trade
    // dto: Trade -> TradeDto
    @GetMapping
    public List<TradeDto> trades() {
        return tradeService.findAll();
    }

    @GetMapping("/count")
    public String count() {
        return Long.toString(tradeService.countItems());
    }

    // http://localhost:8080/trade/1
    @GetMapping("/{id}")
    public Optional<TradeDto> trade(@PathVariable long id) {
        return tradeService.findById(id);
    }
    
}
