package net.lahuta.trdman.controller;

import java.util.List;
import java.util.Optional;

import net.lahuta.trdman.dto.PositionDto;
import net.lahuta.trdman.dto.TradeDto;
import net.lahuta.trdman.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trdman")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    // http://localhost:8080/trdman/trade
    // dto: Trade -> TradeDto
    @GetMapping("/trade")
    public List<TradeDto> trades() {
        return tradeService.findAllTrades();
    }

    // http://localhost:8080/trdman/position
    // dto: Trade -> TradeDto
    @GetMapping("/position")
    public List<PositionDto> positions() {
        return tradeService.findAllPositions();
    }

    @GetMapping("/trade/count")
    public String tradeCount() {
        return Long.toString(tradeService.countTrades());
    }

    @GetMapping("/position/count")
    public String positionCount() {
        return Long.toString(tradeService.countPositions());
    }

    // http://localhost:8080/trdman/trade/1
    @GetMapping("/trade/{id}")
    public Optional<TradeDto> trade(@PathVariable long id) {
        return tradeService.findTradeById(id);
    }

    // http://localhost:8080/trdman/position/1
    @GetMapping("/position/{id}")
    public Optional<PositionDto> position(@PathVariable long id) {
        return tradeService.findPositionById(id);
    }

    @PostMapping("/trade/{id}")
    public void deleteTrade(@PathVariable long id) {
        tradeService.deleteById(id);
    }

    // http://localhost:8080/trade/donetting?key=CBKFR
    @RequestMapping(value = "/donetting", method = RequestMethod.GET)
    @ResponseBody
    public String doNetting(@RequestParam("key") String key) {
        tradeService.netTrades(key);
        return "Netting";
    }
    
}
