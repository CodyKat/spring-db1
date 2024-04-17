package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

@Slf4j
public class UnCheckedAppTest {

    @Test
    void unChecked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    @Test
    void printException() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
            log.info("ex : ", e);
        }

    }
    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }
    static class Service {
        Repository repository = new Repository();
        NetWorkClient netWorkClient = new NetWorkClient();

        public void logic(){
            repository.call();
            netWorkClient.call();
        }
    }
    static class NetWorkClient {
        public void call(){
            throw new RunTimeConnectException("연결 실패");
        }
    }
    static class Repository {
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RunTimeConnectException extends RuntimeException {
        public RunTimeConnectException(String message) {
            super(message);
        }

        public RunTimeConnectException(Throwable cause) {
            super(cause);
        }
    }

    static class RunTimeSqlException extends RuntimeException {
        public RunTimeSqlException(String message) {
            super(message);
        }

        public RunTimeSqlException(Throwable cause) {
            super(cause);
        }
    }
}
