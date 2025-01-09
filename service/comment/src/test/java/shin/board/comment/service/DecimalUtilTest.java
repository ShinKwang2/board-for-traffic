package shin.board.comment.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecimalUtilTest {

    @Test
    void isSameAfterConvert() {
        create("00000");
        create("00001");
        create("00a0Z");
    }

    void create(String origin) {
        int number = DecimalUtil.convertInt(origin);
        String afterConvert = DecimalUtil.convertString(number);
        assertThat(origin).isEqualTo(afterConvert);
    }

    @Test
    @DisplayName("Int로 전환하고 증가시킨 뒤 String으로 전환하면 그만큼 숫자가 늘어난다")
    void increaseAfterConvert() {
        increaseAfterConvert("00000", "00001");
        increaseAfterConvert("0000z", "00010");
        increaseAfterConvert("0zzzz", "10000");
    }

    void increaseAfterConvert(String origin, String expected) {
        int number = DecimalUtil.convertInt(origin);
        number += 1;
        String afterConvert = DecimalUtil.convertString(number);
        assertThat(afterConvert).isEqualTo(expected);
    }
}