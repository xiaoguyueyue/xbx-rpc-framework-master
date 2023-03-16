package github.javaxbx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum LoadBalanceEnum {

    CONSISTENTHASH("consistenthash"),
    RANDOM("random");

    private final String name;
}
