package com.example.testing.bitcoincoursenavigation.ObjectsPojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CryptoData {

    public static void SetValues(CryptoWallet cryptoObject, String id, JSONObject data) throws JSONException {
        cryptoObject.setPrice(getPrice(id, data));
        cryptoObject.setCap(getCap(id, data));
        cryptoObject.setDayChange(getDayChange(id, data));
        cryptoObject.setHourChange(getHourChange(id, data));
        cryptoObject.setWeekChange(getWeekChange(id, data));
    }

    public static String getPrice(String cryptoid, JSONObject dataObject) throws JSONException {
        String price = "";
        JSONObject btcObject = new JSONObject(dataObject.get(cryptoid).toString());
        JSONObject btcQuotes = new JSONObject(btcObject.get("quotes").toString());
        JSONObject btcUSD = new JSONObject(btcQuotes.get("USD").toString());
        price = btcUSD.get("price").toString();
        return price;
    }
    private static String getCap(String cryptoid, JSONObject dataObject) throws JSONException {
        String price = "";
        JSONObject btcObject = new JSONObject(dataObject.get(cryptoid).toString());
        JSONObject btcQuotes = new JSONObject(btcObject.get("quotes").toString());
        JSONObject btcUSD = new JSONObject(btcQuotes.get("USD").toString());
        price = btcUSD.get("market_cap").toString();
        return price;
    }

    private static float getHourChange(String cryptoid, JSONObject dataObject) throws JSONException {
        float price = 0;
        JSONObject btcObject = new JSONObject(dataObject.get(cryptoid).toString());
        JSONObject btcQuotes = new JSONObject(btcObject.get("quotes").toString());
        JSONObject btcUSD = new JSONObject(btcQuotes.get("USD").toString());
        price = Float.parseFloat(btcUSD.get("percent_change_1h").toString());
        return price;
    }
    private static float getDayChange(String cryptoid, JSONObject dataObject) throws JSONException {
        float price = 0;
        JSONObject btcObject = new JSONObject(dataObject.get(cryptoid).toString());
        JSONObject btcQuotes = new JSONObject(btcObject.get("quotes").toString());
        JSONObject btcUSD = new JSONObject(btcQuotes.get("USD").toString());
        price = Float.parseFloat(btcUSD.get("percent_change_24h").toString());
        return price;
    }
    private static float getWeekChange(String cryptoid, JSONObject dataObject) throws JSONException {
        float price = 0;
        JSONObject btcObject = new JSONObject(dataObject.get(cryptoid).toString());
        JSONObject btcQuotes = new JSONObject(btcObject.get("quotes").toString());
        JSONObject btcUSD = new JSONObject(btcQuotes.get("USD").toString());
        price = Float.parseFloat(btcUSD.get("percent_change_7d").toString());
        return price;
    }
}
