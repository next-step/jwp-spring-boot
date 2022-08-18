package racingcar;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarTest {
    @Test
    void 이동() {
        Car car = new Car();
        car.move();
        assertThat(car.position()).isEqualTo(1);
    }

    @Test
    void 정지() {
        Car car = new Car();
        car.move();
        assertThat(car.position()).isEqualTo(0);
    }
}
