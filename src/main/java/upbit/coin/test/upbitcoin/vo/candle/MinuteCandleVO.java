package upbit.coin.test.upbitcoin.vo.candle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinuteCandleVO {

    String market;
    String candle_date_time_utc;
    String candle_date_time_kst;
    String opening_price;
    String high_price;
    String low_price;
    String trade_price;
    String timestamp;
    String candle_acc_trade_price;
    String candle_acc_trade_volume;
    String unit;
}
