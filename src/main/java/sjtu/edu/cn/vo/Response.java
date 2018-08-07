package sjtu.edu.cn.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 丁峰
 * @date 2018/7/11 12:20
 * @see Response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Response {
    public final static String _OK_ = "success";
    public final static String _FAILURE_ = "failure";
    private String status;
    private Object msg;

    public static Response ok(Object obj) {
        log.info("return ok obj={}", obj);
        return new Response(_OK_, obj);
    }

    public static Response ok() {
        log.info("return ok");
        return new Response(_OK_, "");
    }

    public static Response failure(Object error) {
        log.info("return failure = {}", error);
        return new Response(_FAILURE_, error);
    }

    public static Response failure() {
        log.info("return failure");
        return new Response(_FAILURE_, "");
    }

    public Object getMsg() {
        return this.msg;
    }
}
