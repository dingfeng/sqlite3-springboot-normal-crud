package sjtu.edu.cn.bl.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sjtu.edu.cn.bl.service.MemberInfoService;
import sjtu.edu.cn.dao.MemberInfoDao;
import sjtu.edu.cn.entity.MemberInfo;
import sjtu.edu.cn.util.Utils;
import sjtu.edu.cn.vo.MemberInfoVo;
import sjtu.edu.cn.vo.PageVo;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 丁峰
 * @date 2018/7/24 17:11
 * @description TODO
 * @see MemberInfoImpl
 */
@Service
@Slf4j
public class MemberInfoImpl implements MemberInfoService {
    @Autowired
    private MemberInfoDao memberInfoDao;

    @Override
    public PageVo<MemberInfoVo> lessYearSearch(String lessYear, Pageable page) {
        //对学生名字模糊搜索
        Specification<MemberInfo> specification = (Specification<MemberInfo>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.lessThan(root.get("endYear"), lessYear);
            return predicate;
        };
        Page<MemberInfo> memberPage = memberInfoDao.findAll(specification, page);
        List<MemberInfoVo> memberInfoVos = new ArrayList<>();
        for (MemberInfo member : memberPage) {
            memberInfoVos.add(toVo(member));
        }
        PageVo<MemberInfoVo> pageVo = new PageVo<>(memberPage.getTotalPages(), memberPage.getTotalElements(), memberInfoVos);
        return pageVo;
    }

    @Override
    public PageVo<MemberInfoVo> fuzzySearch(String fuzzy, Pageable page) {
        //对学生名字模糊搜索
        Specification<MemberInfo> specification = (Specification<MemberInfo>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + fuzzy + "%");
            return predicate;
        };
        Page<MemberInfo> memberPage = memberInfoDao.findAll(specification, page);
        List<MemberInfoVo> memberInfoVos = new ArrayList<>();
        for (MemberInfo member : memberPage) {
            memberInfoVos.add(toVo(member));
        }
        PageVo<MemberInfoVo> pageVo = new PageVo<>(memberPage.getTotalPages(), memberPage.getTotalElements(), memberInfoVos);
        return pageVo;
    }

    @Override
    public PageVo<MemberInfoVo> exampleSearch(MemberInfo example, Pageable page) {
        List<MemberInfoVo> memberVos = new ArrayList<>();
        Page<MemberInfo> memberPage = memberInfoDao.findAll(Example.of(example, ExampleMatcher.matching().withIgnoreNullValues()), page);
        for (MemberInfo member : memberPage) {
            memberVos.add(toVo(member));
        }
        PageVo<MemberInfoVo> pageVo = new PageVo<>(memberPage.getTotalPages(), memberPage.getTotalElements(), memberVos);
        return pageVo;
    }

    @Override
    public MemberInfoVo findOne(String name, String telephone) {
        MemberInfo example = new MemberInfo();
        example.setName(name);
        example.setTelephone(telephone);
        example.setStatus(0);
        Optional<MemberInfo> memberInfoOptional = memberInfoDao.findOne(Example.of(example, ExampleMatcher.matching().withIgnoreNullValues()));
        if (memberInfoOptional.isPresent()) {
            MemberInfo memberInfo = memberInfoOptional.get();
            return toVo(memberInfo);
        }
        return null;
    }

    private MemberInfoVo toVo(MemberInfo memberInfo) {
        MemberInfoVo memberInfoVo = new MemberInfoVo();
        Utils.nonNullCopyProperties(memberInfo, memberInfoVo);
        memberInfoVo.setCreatedAt(Utils.formatDate(memberInfo.getCreatedAt()));
        memberInfoVo.setUpdatedAt(Utils.formatDate(memberInfo.getUpdatedAt()));
        return memberInfoVo;
    }

    @Override
    public boolean update(MemberInfoVo memberInfoVo) {
        if (memberInfoVo.getId() == null) return false;
        Long id = memberInfoVo.getId();
        MemberInfo memberInfo = memberInfoDao.getOne(id);
        Utils.nonNullCopyProperties(memberInfoVo, memberInfo);
        memberInfoDao.save(memberInfo);
        return true;
    }

    @Override
    public boolean add(MemberInfoVo memberInfoVo) {
        MemberInfo memberInfo = new MemberInfo();
        Utils.nonNullCopyProperties(memberInfoVo, memberInfo);
        memberInfoDao.save(memberInfo);
        return true;
    }
}
