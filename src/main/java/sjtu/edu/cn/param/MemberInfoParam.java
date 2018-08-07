package sjtu.edu.cn.param;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * @author 丁峰
 * @date 2018/7/24 17:13
 * @description TODO
 * @see MemberInfoParam
 */
@Data
@NoArgsConstructor
public class MemberInfoParam {
    private Long id;
    //姓名
    private String name;
    //company
    private String company;
    //telephone
    private String telephone;
    //小于年份
    private String lessYear;
    //截至年份
    private String endYear;
    //会员号
    private String memberNo;
    //状态 -1为删除 0为正常
    private Integer status;
    //更新时间
    private Date updatedAt;
    //创建时间
    private Date createdAt;
    //page
    private PageParam page;
    //fuzzy
    private String fuzzy;
}
