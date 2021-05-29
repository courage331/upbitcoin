package upbit.coin.test.upbitcoin.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import upbit.coin.test.upbitcoin.vo.ResponseVO;
import upbit.coin.test.upbitcoin.vo.accounts.AccountsVO;
import upbit.coin.test.upbitcoin.vo.candle.MinuteCandleVO;
import upbit.coin.test.upbitcoin.vo.market.MarketCodeVO;
import upbit.coin.test.upbitcoin.vo.orderbook.OrderBookUnits;
import upbit.coin.test.upbitcoin.vo.orderbook.OrderBookVO;


import java.net.InetAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UpbitTestService {

    @Autowired
    Gson gson;

    @Value("${upbit.address.market}")
    String marketurl;

    @Value("${upbit.address.minuteCandle}")
    String candleurl;

    @Value("${upbit.address.orderbook}")
    String orderbookurl;

    @Value("${upbit.address.accounts}")
    String accountsurl;

    @Value("${upbit.key.AccessKey}")
    String accessKey;

    @Value("${upbit.key.SecretKey}")
    String secretKey;

    public ResponseVO showMarketList() {
        ResponseVO responseVO = new ResponseVO();
        RestTemplate restTemplate = new RestTemplate();


        try {
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

            HttpHeaders headers = new HttpHeaders();

            headers.add("Accept", "application/json");

            final HttpEntity<String> entity = new HttpEntity(headers);

            ResponseEntity<String> response = restTemplate.exchange(marketurl, HttpMethod.GET, entity, String.class);
            JsonArray jsonObj = gson.fromJson(response.getBody(), JsonArray.class);


            int len = jsonObj.size();
            List<MarketCodeVO> marketCodeVOList = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                MarketCodeVO marketCodeVO = new MarketCodeVO();
                marketCodeVO.setMarket(jsonObj.get(i).getAsJsonObject().get("market").getAsString());
                marketCodeVO.setKorean_name(jsonObj.get(i).getAsJsonObject().get("korean_name").getAsString());
                marketCodeVO.setEnglish_name(jsonObj.get(i).getAsJsonObject().get("english_name").getAsString());
                marketCodeVOList.add(marketCodeVO);
            }

//            System.out.println(jsonObj.get(0).getAsJsonObject().get("english_name").getAsString());
//            System.out.println(jsonObj.get(0).getAsJsonObject().get("market").getAsString());
//
//
//            System.out.println(jsonObj.get(1).getAsJsonObject().get("english_name").getAsString());
//            System.out.println(jsonObj.get(1).getAsJsonObject().get("market").getAsString());
            responseVO.setResponseCode(200);
            responseVO.setResponseMessage("Success");
            responseVO.setData(marketCodeVOList);

            for (int i = 0; i < marketCodeVOList.size(); i++) {
                System.out.println(marketCodeVOList.get(i).getMarket());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseVO;
    }

    public ResponseVO showMinuteCandle(Map<String, Object> reqMap) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String toDate = sdf.format(date);

        int unit = (int) (reqMap.get("unit"));
        String market = String.valueOf(reqMap.get("market"));
        String to = String.valueOf(reqMap.get("to")).equals("") ? toDate : String.valueOf(reqMap.get("to"));
        int count = (int) (reqMap.get("count"));

        ResponseVO responseVO = new ResponseVO();

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

            HttpHeaders headers = new HttpHeaders();

            headers.add("Accept", "application/json");

            final HttpEntity<String> entity = new HttpEntity(headers);

            candleurl += "/" + unit + "?market=" + market + "&to=" + to + "&count=" + count;


            ResponseEntity<String> response = restTemplate.exchange(candleurl, HttpMethod.GET, entity, String.class);
            JsonArray jsonObj = gson.fromJson(response.getBody(), JsonArray.class);


            int len = jsonObj.size();
            List<MinuteCandleVO> minuteCandleList = new ArrayList();
            for (int i = 0; i < len; i++) {
                MinuteCandleVO minuteCandleVO = new MinuteCandleVO();
                minuteCandleVO.setMarket(jsonObj.get(i).getAsJsonObject().get("market").getAsString());
                minuteCandleVO.setCandle_date_time_utc(jsonObj.get(i).getAsJsonObject().get("candle_date_time_utc").getAsString());
                minuteCandleVO.setCandle_date_time_kst(jsonObj.get(i).getAsJsonObject().get("candle_date_time_kst").getAsString());
                minuteCandleVO.setOpening_price(jsonObj.get(i).getAsJsonObject().get("opening_price").getAsString());
                minuteCandleVO.setHigh_price(jsonObj.get(i).getAsJsonObject().get("high_price").getAsString());
                minuteCandleVO.setLow_price(jsonObj.get(i).getAsJsonObject().get("low_price").getAsString());
                minuteCandleVO.setTrade_price(jsonObj.get(i).getAsJsonObject().get("trade_price").getAsString());
                minuteCandleVO.setTimestamp(jsonObj.get(i).getAsJsonObject().get("timestamp").getAsString());
                minuteCandleVO.setCandle_acc_trade_price(jsonObj.get(i).getAsJsonObject().get("candle_acc_trade_price").getAsString());
                minuteCandleVO.setCandle_acc_trade_volume(jsonObj.get(i).getAsJsonObject().get("candle_acc_trade_volume").getAsString());
                minuteCandleVO.setUnit(jsonObj.get(i).getAsJsonObject().get("unit").getAsString());
                minuteCandleList.add(minuteCandleVO);
            }
            responseVO.setResponseCode(200);
            responseVO.setResponseMessage("Success");
            responseVO.setData(minuteCandleList);
            System.out.println(InetAddress.getLocalHost());
            //System.out.println(response.getHeaders());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return responseVO;
    }

    public ResponseVO showOrderBook(String markets) {

        ResponseVO responseVO = new ResponseVO();

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

            HttpHeaders headers = new HttpHeaders();

            headers.add("Accept", "application/json");

            final HttpEntity<String> entity = new HttpEntity(headers);

            orderbookurl += "?markets=" + markets;
            //System.out.println(orderbookurl);

            ResponseEntity<String> response = restTemplate.exchange(orderbookurl, HttpMethod.GET, entity, String.class);

            JsonArray jsonObj = gson.fromJson(response.getBody(), JsonArray.class);

            int len = jsonObj.size();
            List<OrderBookVO> orderBookVOList = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                OrderBookVO orderBookVO = new OrderBookVO();
                orderBookVO.setMarket(jsonObj.get(i).getAsJsonObject().get("market").getAsString());
                orderBookVO.setTimestamp(jsonObj.get(i).getAsJsonObject().get("timestamp").getAsString());
                orderBookVO.setTotal_ask_size(jsonObj.get(i).getAsJsonObject().get("total_ask_size").getAsString());
                orderBookVO.setTotal_bid_size(jsonObj.get(i).getAsJsonObject().get("total_bid_size").getAsString());

                JsonArray jsonObj2 = gson.fromJson(jsonObj.get(i).getAsJsonObject().get("orderbook_units"), JsonArray.class);
                List<OrderBookUnits> orderBookUnitsList = new ArrayList<>();
                for (int j = 0; j < jsonObj2.size(); j++) {
                    OrderBookUnits orderBookUnits = new OrderBookUnits();
                    orderBookUnits.setAsk_price(jsonObj2.get(j).getAsJsonObject().get("ask_price").getAsInt());
                    orderBookUnits.setBid_price(jsonObj2.get(j).getAsJsonObject().get("bid_price").getAsInt());
                    orderBookUnits.setAsk_size(jsonObj2.get(j).getAsJsonObject().get("ask_size").getAsString());
                    orderBookUnits.setBid_size(jsonObj2.get(j).getAsJsonObject().get("bid_size").getAsString());

                    orderBookUnitsList.add(orderBookUnits);
                }
                orderBookVO.setOrderbook_units(orderBookUnitsList);

                orderBookVOList.add(orderBookVO);
            }
            responseVO.setResponseCode(200);
            responseVO.setResponseMessage("Success");
            responseVO.setData(orderBookVOList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseVO;

    }

    public ResponseVO showAccounts() {


        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        RestTemplate restTemplate = new RestTemplate();

        ResponseVO responseVO = new ResponseVO();

        try {


            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

            HttpHeaders headers = new HttpHeaders();

            headers.add("Accept", "application/json");
            headers.add("Authorization", authenticationToken);
            final HttpEntity<String> entity = new HttpEntity(headers);

            ResponseEntity<String> response = restTemplate.exchange(accountsurl, HttpMethod.GET, entity, String.class);

            JsonArray jsonArray = gson.fromJson(response.getBody(), JsonArray.class);
            int len = jsonArray.size();
            if (len == 0) {
                responseVO.setResponseCode(200);
                responseVO.setResponseMessage("No Accounts");
                responseVO.setData(null);
            } else {
                List<AccountsVO> accountsVOList = new ArrayList<>();
                for (int i = 0; i < len; i++) {
                    AccountsVO accountsVO = new AccountsVO();
                    accountsVO.setCurrency(jsonArray.get(i).getAsJsonObject().get("currency").getAsString());
                    accountsVO.setBalance(jsonArray.get(i).getAsJsonObject().get("balance").getAsString());
                    accountsVO.setLocked(jsonArray.get(i).getAsJsonObject().get("locked").getAsString());
                    accountsVO.setAvg_buy_price(jsonArray.get(i).getAsJsonObject().get("avg_buy_price").getAsString());
                    accountsVO.setAvg_buy_price_modifies(jsonArray.get(i).getAsJsonObject().get("avg_buy_price_modified").getAsString());
                    accountsVO.setUnit_currency(jsonArray.get(i).getAsJsonObject().get("unit_currency").getAsString());
                    accountsVOList.add(accountsVO);
                }
                responseVO.setResponseCode(200);
                responseVO.setResponseMessage("Success");
                responseVO.setData(accountsVOList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseVO;
    }
}
