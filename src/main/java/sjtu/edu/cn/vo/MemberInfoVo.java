package sjtu.edu.cn.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 丁峰
 * @date 2018/7/24 17:15
 * @description TODO
 * @see MemberInfoVo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoVo {
    private Long id;
    //姓名
    private String name;
    //单位
    private String company;
    //手机号码
    private String telephone;
    //缴费截至日期
    private String endYear;
    //会员名
    private String memberNo;
    //状态 删除:-1，正常：0，已发布：1
    private Integer status;
    private String createdAt;
    private String updatedAt;
}
