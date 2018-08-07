package sjtu.edu.cn.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 丁峰
 * @date 2018/7/16 20:24
 * @see PageVo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> {
    private Integer totalPages;
    private Long totalElements;
    private List<T> data;
}
