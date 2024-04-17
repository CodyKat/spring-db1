package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {


    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }
    static class MyCheckedException extends Exception {

        /*
        Exception을 상속받은 예외는 checked 예외가 된다.
         */
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /*
        Checked 예외는
        예외를 잡아서 처리하거나, 던지거나 둘 중 하나를 필수로 선택해야한다.
     */
    static class Service {
        Repository repository = new Repository();

        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("예외 처리 = {}", e.getMessage(), e);
            }
        }

        /*
            체크 예외를 밖으로 던지는 코드
            체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야한다.
            @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }
}
