package com.reto.exchangeRate.util;

public class Constants {
    public final static String MAIN_PATH = "/exchange-rate";
    public final static String ID = "/{id}";
    public final static String UPDATE_OPERATION = "/update-exchange-rate/{id}";
    public final static String SEARCH_RATE = "/search-rate";
    public final static String UPDATE_RATE = "/update-rate";
    public final static String SAVE_RATE = "/save-rate";
    public final static String SEARCH_RATE_CURRENCY = "/search-rate/{currency}";


    public final static String SAVE_VALUE="Method through which the exchange rate information is sent to be recorded in the database";
    public final static String SAVE_NOTE="To register the exchange rate, it will be necessary to fill in all the fields, with the exception of the Id's, which will be generated automatically.";

}
