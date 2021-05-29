package upbit.coin.test.upbitcoin.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseVO {

    private int responseCode;
    private String responseMessage;
    private Object Data;
}
