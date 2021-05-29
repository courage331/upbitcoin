package upbit.coin.test.upbitcoin.vo.orderbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookUnits {

    int ask_price;
    int bid_price;
    String ask_size;
    String bid_size;
}
