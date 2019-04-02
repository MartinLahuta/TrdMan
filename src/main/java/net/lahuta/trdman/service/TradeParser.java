package net.lahuta.trdman.service;

import lombok.extern.slf4j.Slf4j;
import net.lahuta.trdman.entity.Trade;
import net.lahuta.trdman.entity.TradeEnum;
import net.lahuta.trdman.exceptions.TradeParseException;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.EnumSet;

@Slf4j
@Service
public class TradeParser extends DefaultHandler {

    private final XMLReader xmlReader;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyMMdd");

    // xml variables:
    private Trade trade;
    private int level;
    private EnumSet<TradeEnum> tradeValues;

    private BigDecimal quantity;
    private BigDecimal amount;
    private BigDecimal price;
    private String buySellTyp;
    private String currency;
    private String trdNo;
    private String isin;
    private String memberId;
    private LocalDate stlDate;

    public TradeParser() throws SAXException {
        xmlReader = XMLReaderFactory.createXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.setErrorHandler(this);
    }

    public Trade parseXMLTrade(final String xml) throws TradeParseException {

        this.trade = new Trade();
        this.level = 0;
        this.tradeValues = EnumSet.noneOf(TradeEnum.class);

        InputSource is = new InputSource(new StringReader(xml));

        try {
            xmlReader.parse(is);
        } catch(IOException e) {
            log.error("XML parser: IO error");
            throw new RuntimeException();
        } catch(SAXException e) {
            log.error(e.getMessage());
            throw new TradeParseException(e.getMessage());
        }


        if (!TradeEnum.containsAll(this.tradeValues)) {
            log.info("Missing values:");
            for(TradeEnum value : TradeEnum.values()) {
                if (!this.tradeValues.contains(value)) {
                    log.info("Missing value: {}", value.getValueName());
                }
            }
            throw new TradeParseException("missing values");
        }

        return trade;
    }

    /*
     * SAX parser
     */

    @Override
    public void startDocument() {
        log.info("startDocument()");
    }

    @Override
    public void endDocument() {
        log.info("endDocument()");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws TradeParseException {
        level++;
        int nattrs = attrs.getLength();

        if(level == 1 && qName.equals("CCPTrdRpt")) {
            /* parse CCPTrdRpt values */
            for(int iattr = 0; iattr < nattrs; iattr++) {

                String attrName = attrs.getLocalName(iattr);
                String attrValue = attrs.getValue(iattr);

                if (attrName.equals("TrdNo")) {
                    if (attrValue.length() > 10) {
                        throw new TradeParseException("length(TrdNo)>10: '" + attrValue + "'");
                    }
                    trade.setTrdNo(attrValue);
                    tradeValues.add(TradeEnum.TRDNO);
                } else if(attrName.equals("ISIN")) {
                    if (attrValue.length() > 12) {
                        throw new TradeParseException("length(ISIN)>10: '" + attrValue + "'");
                    }
                    trade.setIsin(attrValue);
                    tradeValues.add(TradeEnum.ISIN);
                } else if(attrName.equals("BuySellFlag")) {
                    if (attrValue.compareToIgnoreCase("B") != 0 && attrValue.compareToIgnoreCase("S") != 0) {
                        throw new TradeParseException("BuySellTyp <> B/S: '" + attrValue + "'");
                    }
                    trade.setBuySellTyp(attrValue);
                    tradeValues.add(TradeEnum.BUYSELLTYP);
                } else if(attrName.equals("Qty")) {
                    try {
                        BigDecimal qty = new BigDecimal(attrValue);
                        trade.setQuantity(qty);
                    } catch(NumberFormatException e) {
                        throw new TradeParseException("Invalid Qty value: '" + attrValue + "'");
                    }
                    tradeValues.add(TradeEnum.QUANTITY);
                } else if(attrName.equals("PayableAmnt")) {
                    try {
                        BigDecimal amnt = new BigDecimal(attrValue);
                        trade.setAmount(amnt);
                    } catch(NumberFormatException e) {
                        throw new TradeParseException("Invalid PayableAmnt value: '" + attrValue + "'");
                    }
                    tradeValues.add(TradeEnum.AMOUNT);
                } else if(attrName.equals("TrdPrc")) {
                    try {
                        BigDecimal price = new BigDecimal(attrValue);
                        trade.setPrice(price);
                    } catch(NumberFormatException e) {
                        throw new TradeParseException("Invalid TrdPrice value: '" + attrValue + "'");
                    }
                    tradeValues.add(TradeEnum.PRICE);
                } else if(attrName.equals("StlCurr")) {
                    if (attrValue.length() > 3) {
                        throw new TradeParseException("length(StlCurr)>3: '" + attrValue + "'");
                    }
                    trade.setCurrency(attrValue);
                    tradeValues.add(TradeEnum.CURRENCY);
                } else if(attrName.equals("MemberID")) {
                    if (attrValue.length() > 5) {
                        throw new TradeParseException("length(MemberID)>5: '" + attrValue + "'");
                    }
                    trade.setMemberId(attrValue);
                    tradeValues.add(TradeEnum.MEMBERID);
                } else if(attrName.equals("StlDt")) {
                    try {
                        LocalDate date = LocalDate.parse(attrValue, dtf);
                        trade.setStlDate(date);
                    } catch(DateTimeParseException e) {
                        throw new TradeParseException("Invalid StlDt value: '" + attrValue + "'");
                    }
                    tradeValues.add(TradeEnum.STLDATE);
                } else {
                    continue;
                }

                log.info("value: {}={}", attrName, attrValue);

            }
            return;
        }

//        log.info("startElement({},{},{}): level={}", localName, qName, nattrs, level);
//        if (nattrs > 0) {
//            for(int iattr = 0; iattr < nattrs; iattr++) {
//                log.info("{}: {}: {}={}", iattr, attrs.getType(iattr), attrs.getLocalName(iattr), attrs.getValue(iattr));
//            }
//        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        level--;
//        log.info("endElement({},{})", localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
//        log.info("CHARS: len={}", length);
    }

}
