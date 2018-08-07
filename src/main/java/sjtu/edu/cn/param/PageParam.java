package sjtu.edu.cn.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author 丁峰
 * @date 2018/7/14 10:13
 * @see PageParam
 */
@Data
@NoArgsConstructor
public class PageParam {
    private Integer page = 0;
    private Integer size = 10;
    private List<SortParam> sorts;

    public static PageParam defaultObject() {
        PageParam pageParam = new PageParam();
        pageParam.setSorts(Arrays.asList(new SortParam[]{new SortParam("createdAt", "desc")}));
        return pageParam;
    }
}
