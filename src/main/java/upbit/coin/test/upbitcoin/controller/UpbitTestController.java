package upbit.coin.test.upbitcoin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upbit.coin.test.upbitcoin.service.UpbitTestService;
import upbit.coin.test.upbitcoin.vo.ResponseVO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upbit")
public class UpbitTestController {

    @Autowired
    UpbitTestService upbitTestService;

    //시장 조회
    @RequestMapping(method = RequestMethod.GET, value = "/accounts")
    public ResponseVO showMarket(){
        ResponseVO responseVO  = upbitTestService.showMarketList();
        return responseVO;
    }

    //시세 캔들
    //PathVariable : 분 단위. 가능한 값 : 1, 3, 5, 15, 10, 30, 60, 240

    /**
     * RequestParam
     *
     * market: String -- 마켓 코드 (ex. KRW-BTC)
     *
     * to: String --마지막 캔들 시각 (exclusive). 포맷 : yyyy-MM-dd'T'HH:mm:ss'Z' or yyyy-MM-dd HH:mm:ss. 비워서 요청시 가장 최근 캔들
     *
     * count : int -- 캔들 갯수(최대 200개)
     *
     * */
    @RequestMapping(method = RequestMethod.GET, value="/minute/candle/{unit}")
    public ResponseVO showCandle(
            @PathVariable("unit") int unit ,
            @RequestParam(value="market") String market,
            @RequestParam(value="to", required=false, defaultValue = "") String to,
            @RequestParam(value="count", required = false, defaultValue = "200") int count

    ){
        Map<String,Object> reqMap = new HashMap <>();
        reqMap.put("unit",unit);
        reqMap.put("market",market);
        reqMap.put("to",to);
        reqMap.put("count",count);

        ResponseVO responseVO = upbitTestService.showMinuteCandle(reqMap);


        return responseVO;
    }

    @RequestMapping(method = RequestMethod.GET, value="/orderbook")
    public ResponseVO showOrderBook(
            @RequestParam("markets") String markets){


        ResponseVO responseVO = upbitTestService.showOrderBook(markets);

        return responseVO;
    }

}
