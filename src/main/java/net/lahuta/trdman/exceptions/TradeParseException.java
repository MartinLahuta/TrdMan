package net.lahuta.trdman.exceptions;

import org.xml.sax.SAXException;

public class TradeParseException extends SAXException {

    public TradeParseException(String errorMsg) {
        super(errorMsg);
    }

}
