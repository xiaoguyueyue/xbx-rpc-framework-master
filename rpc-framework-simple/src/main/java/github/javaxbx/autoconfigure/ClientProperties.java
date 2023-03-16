package github.javaxbx.autoconfigure;

import github.javaxbx.enums.LoadBalanceEnum;
import lombok.Data;

/**
 * @Author xiaobiaoxu
 * @Date 2023年03月15日 18:57
 */
@Data
public class ClientProperties {
    private String loadBalance = LoadBalanceEnum.CONSISTENTHASH.getName();
}
