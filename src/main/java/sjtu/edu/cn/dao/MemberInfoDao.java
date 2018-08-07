package sjtu.edu.cn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sjtu.edu.cn.entity.MemberInfo;

/**
 * @author 丁峰
 * @date 2018/7/24 17:09
 * @description TODO
 * @see MemberInfoDao
 */
public interface MemberInfoDao extends JpaRepository<MemberInfo, Long>, JpaSpecificationExecutor<MemberInfo> {
}
