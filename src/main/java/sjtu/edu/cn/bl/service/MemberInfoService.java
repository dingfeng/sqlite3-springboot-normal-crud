package sjtu.edu.cn.bl.service;

import org.springframework.data.domain.Pageable;
import sjtu.edu.cn.entity.MemberInfo;
import sjtu.edu.cn.vo.MemberInfoVo;
import sjtu.edu.cn.vo.PageVo;

import java.util.List;

/**
 * @author 丁峰
 * @date 2018/7/24 17:12
 * @description TODO
 * @see MemberInfoService
 */
public interface MemberInfoService {

    PageVo<MemberInfoVo> lessYearSearch(String lessYear, Pageable page);

    PageVo<MemberInfoVo> fuzzySearch(String fuzzy, Pageable page);

    PageVo<MemberInfoVo> exampleSearch(MemberInfo memberInfo, Pageable page);

    MemberInfoVo findOne(String name, String telephone);

    boolean update(MemberInfoVo memberInfoVo);

    boolean add(MemberInfoVo memberInfoVo);
}
