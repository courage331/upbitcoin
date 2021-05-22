package upbit.coin.test.upbitcoin.service;

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
import upbit.coin.test.upbitcoin.vo.candle.MinuteCandleList;
import upbit.coin.test.upbitcoin.vo.candle.MinuteCandleVO;
import upbit.coin.test.upbitcoin.vo.market.MarketCodeVO;
import upbit.coin.test.upbitcoin.vo.ResponseVO;


import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UpbitTestService {

    @Autowired
    Gson gson;
    ResponseVO responseVO = new ResponseVO();

    String AccessKey = "C4Ev6zIuWd35DX1b1Mjx1pvYVr8DAVPigTIVYLmS";
    String SecretKey = "iP6ovLpHhxASqjv9l1oIdk6TW2tXZ8ITjm1Pzp9R";

    @Value("${upbit.address.market}")
    String marketurl;

    @Value("${upbit.address.minuteCandle}")
    String candleurl;

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

            responseVO.setData(minuteCandleList);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return responseVO;
    }
}
