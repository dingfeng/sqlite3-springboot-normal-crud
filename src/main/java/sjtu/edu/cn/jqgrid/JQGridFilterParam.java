package sjtu.edu.cn.jqgrid;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 丁峰
 * @date 2018/7/19 15:38
 * @description TODO
 * @see JQGridFilterParam
 */
@Data
@NoArgsConstructor
public class JQGridFilterParam {
    private String groupOp;
    private List<JQGridRule> rules;
}
