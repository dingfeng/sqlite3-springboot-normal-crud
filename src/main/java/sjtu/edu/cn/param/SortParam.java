package sjtu.edu.cn.param;

import lombok.*;

/**
 * @author 丁峰
 * @date 2018/7/14 10:13
 * @see SortParam
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortParam {
    @NonNull
    private String attr;
    @NonNull
    private String direction;
}
