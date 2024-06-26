package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUnCheckedException.class);
    }

    /*
        RunTimeException을 받은 예외는 UnChecked 예외가 된다.
     */
    static class MyUnCheckedException extends RuntimeException {
        public MyUnCheckedException(String message) {
            super(message);
        }
    }

    /*
        UnChecked 예외는 예외를 잡거나 던지지 않아도 된다.
     */
    static class Service {
        Repository repository = new Repository();
        /**
         * 필요한 경우 예외를 잡아서 처리하면 된다.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUnCheckedException e) {
                log.info("예외 처리, message = {}", e.getMessage(), e);
            }
        }
        public void callThrow() {
            repository.call();
        }
    }
    static class Repository {
        public void call() {
            throw new MyUnCheckedException("ex");
        }
    }
}
