package sjtu.edu.cn.jqgrid;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author 丁峰
 * @date 2018/7/19 19:13
 * @description TODO
 * @see JQGridParam
 */
@Data
@NoArgsConstructor
public class JQGridParam {
    private String search;
    private String filters;
    private Integer rows;
    private Integer page;
    private String sidx;
    private String sord;
    private Map<String,Object> others;
}
