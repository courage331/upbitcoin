package upbit.coin.test.upbitcoin.vo.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsVO {
    String currency;
    String balance;
    String locked;
    String avg_buy_price;
    String avg_buy_price_modifies;
    String unit_currency;
}
