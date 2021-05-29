package upbit.coin.test.upbitcoin.vo.orderbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookVO {
    String market;
    String timestamp;
    String total_ask_size;
    String total_bid_size;
    List<OrderBookUnits> orderbook_units;

}
