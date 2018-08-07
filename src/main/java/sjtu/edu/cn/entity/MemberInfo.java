package sjtu.edu.cn.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 丁峰
 * @date 2018/7/24 17:19
 * @description TODO
 * @see MemberInfo
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class MemberInfo {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    //姓名
    @Column(columnDefinition = "char(20)")
    private String name;
    //单位
    private String company;
    //手机号码
    @Column(columnDefinition = "char(20)")
    private String telephone;
    //缴费截至日期
    @Column(columnDefinition = "char(4)")
    private String endYear;
    //会员名
    private String memberNo;
    //状态 删除:-1，正常：0，已发布：1
    private Integer status;
    //创建时间
    @CreationTimestamp
    private Date createdAt;
    //更新时间
    @UpdateTimestamp
    private Date updatedAt;
}
