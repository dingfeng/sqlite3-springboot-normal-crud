package sjtu.edu.cn.jqgrid;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 丁峰
 * @date 2018/7/19 15:47
 * @description TODO
 * @see JQGridRule
 */
@Data
@NoArgsConstructor
public class JQGridRule {
    private String field;
    private String op;
    private String data;
}
