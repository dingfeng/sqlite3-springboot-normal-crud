package sjtu.edu.cn.controller.api;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sjtu.edu.cn.bl.service.MemberInfoService;
import sjtu.edu.cn.entity.MemberInfo;
import sjtu.edu.cn.jqgrid.*;
import sjtu.edu.cn.param.MemberInfoParam;
import sjtu.edu.cn.util.Utils;
import sjtu.edu.cn.vo.*;

import java.util.Map;


/**
 * 课程管理。只可更新状态，不可修改其他信息
 * /course
 *
 * @author 丁峰
 * @date 2018/7/16 14:43
 * @see MemberInfoController
 */
@Controller
@RequestMapping("/")
@ConfigurationProperties(prefix = "memberinfo")
@Data
@Slf4j
public class MemberInfoController {
    @Autowired
    private MemberInfoService memberInfoService;
    //搜索
    @RequestMapping("/member")
    public String memberSearch(String searchName, String searchTelephone, Model m) {
        log.info(" memberSearch name={} telephone={}", searchName, searchTelephone);
        if (StringUtils.isEmpty(searchName)) {
            searchName = null;
        }
        if (StringUtils.isEmpty(searchTelephone)) {
            searchTelephone = null;
        }
        MemberInfoVo memberInfoVo = null;
        try {
            memberInfoVo = memberInfoService.findOne(searchName, searchTelephone);
        } catch (Exception e) {
        }
        m.addAttribute("m", memberInfoVo);
        if (memberInfoVo == null && (searchName != null || searchTelephone != null)) {
            m.addAttribute("message", "该用户不存在");
        }
        return "memberSearch";
    }

    //搜索
    @RequestMapping("/manager")
    public String memberSearch(String token, Model m) {
        log.info(" memberSearch name={} telephone={}", token);
        m.addAttribute("token", token);
        return "memberAdmin";
    }

    @RequestMapping("/manager/search")
    @ResponseBody
    public Response search(String token, @RequestBody JQGridParam param) {
        Map<String, Object> map = Utils.toMap(param);
        String json = Utils.toJSON(map);
        MemberInfoParam memberInfoParam = Utils.parse(json, MemberInfoParam.class);
        return search(token, memberInfoParam);
    }

    public Response search(String token, MemberInfoParam memberInfoParam) {
        log.info("search param={}", memberInfoParam);

        String lessYear = memberInfoParam.getLessYear();
        String fuzzy = memberInfoParam.getFuzzy();
        Pageable page = Utils.convert(memberInfoParam.getPage());
        if (lessYear != null) {
            PageVo<MemberInfoVo> pageVo = memberInfoService.lessYearSearch(lessYear, page);
            return Response.ok(pageVo);
        }
        if (fuzzy != null) {
            PageVo<MemberInfoVo> pageVo = memberInfoService.fuzzySearch(fuzzy, page);
            return Response.ok(pageVo);
        }
        MemberInfo example = new MemberInfo();
        Utils.nonNullCopyProperties(memberInfoParam, example);
        PageVo<MemberInfoVo> pageVo = memberInfoService.exampleSearch(example, page);
        return Response.ok(pageVo);
    }

    @RequestMapping("/manager/add")
    @ResponseBody
    public Response add(String token, @RequestBody MemberInfoParam memberInfoParam) {
        log.info("search param={}", memberInfoParam);
        String name = memberInfoParam.getName();
        if (name == null) return Response.failure("add no name");
        MemberInfoVo memberInfoVo = new MemberInfoVo();
        Utils.nonNullCopyProperties(memberInfoParam, memberInfoVo);
        boolean result = memberInfoService.add(memberInfoVo);
        if (result) {
            return Response.ok();
        }
        return Response.failure();
    }

    @RequestMapping("/manager/update")
    @ResponseBody
    public Response update(String token, @RequestBody MemberInfoParam memberInfoParam) {
        log.info("search param={}", memberInfoParam);
        if (!Utils.isNotNull(memberInfoParam.getId(), memberInfoParam.getName())) {
            log.info("invalid params");
            return Response.failure();
        }
        MemberInfoVo memberInfoVo = new MemberInfoVo();
        Utils.nonNullCopyProperties(memberInfoParam, memberInfoVo);
        boolean result = memberInfoService.update(memberInfoVo);
        if (result) {
            return Response.ok();
        }
        return Response.failure();
    }


    @ExceptionHandler(Throwable.class)
    public Response handleException(Throwable e) {
        log.error("error", e);
        return Response.failure();
    }
}
