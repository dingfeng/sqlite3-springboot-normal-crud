package sjtu.edu.cn.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import sjtu.edu.cn.jqgrid.JQGridFilterParam;
import sjtu.edu.cn.jqgrid.JQGridParam;
import sjtu.edu.cn.jqgrid.JQGridRule;
import sjtu.edu.cn.param.PageParam;
import sjtu.edu.cn.param.SortParam;

import javax.persistence.criteria.Predicate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 丁峰
 * @date 2018/7/10 20:53
 * @see Utils
 */
@Slf4j
public class Utils {
    public final static String ASC = "asc";
    public final static String DESC = "desc";
    public final static String DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

    public static boolean ObjAttrEquals(Object o1, Object o2) {
        if ((o1 == null && o2 != null) || (o2 == null && o1 != null)) return false;
        if (!o1.getClass().equals(o2.getClass())) return false;
        Field[] fields = o1.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            try {
                if (!field.get(o1).equals(field.get(o2))) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                log.error("error", e);
                return false;
            }
        }
        return true;
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    public static boolean isNotNull(Object... objects) {
        for (Object object : objects) {
            if (null == object) return false;
        }
        return true;
    }


    public static Pageable convert(PageParam pageParam, Map<String, String> map) {
        if (map == null) map = new HashMap<>();
        if (null == pageParam) {
            pageParam = PageParam.defaultObject();
        }
        if (Utils.isNotNull(pageParam, pageParam.getPage(), pageParam.getSize())) {
            int page = pageParam.getPage();
            int size = pageParam.getSize();
            List<SortParam> sorts = pageParam.getSorts();
            if (null == sorts) {
                sorts = new ArrayList<>();
            }
            if (sorts.isEmpty()) {
                sorts.add(new SortParam("createdAt", DESC));
            }
            List<Sort.Order> orders = new ArrayList<>();
            for (SortParam sortParam : sorts) {
                String attr = sortParam.getAttr();
                if (map.containsKey(attr)) {
                    attr = map.get(attr);
                }
                switch (sortParam.getDirection()) {
                    case ASC:
                        orders.add(new Sort.Order(Sort.Direction.ASC, attr));
                        break;
                    case DESC:
                        orders.add(new Sort.Order(Sort.Direction.DESC, attr));
                        break;
                    default:
                        log.error("wrong sort = {}", sortParam.getDirection());
                        return null;
                }
            }
            Sort sort = Sort.by(orders);
            return PageRequest.of(page, size, sort);
        }
        return null;
    }

    public static Pageable convert(PageParam pageParam) {
        if (null == pageParam) {
            pageParam = PageParam.defaultObject();
        }
        if (Utils.isNotNull(pageParam, pageParam.getPage(), pageParam.getSize())) {
            int page = pageParam.getPage();
            int size = pageParam.getSize();
            List<SortParam> sorts = pageParam.getSorts();
            if (null == sorts) {
                sorts = new ArrayList<>();
            }
            if (sorts.isEmpty()) {
                sorts.add(new SortParam("updatedAt", DESC));
            }
            List<Sort.Order> orders = new ArrayList<>();
            for (SortParam sortParam : sorts) {
                String attr = sortParam.getAttr();
                switch (sortParam.getDirection()) {
                    case ASC:
                        orders.add(new Sort.Order(Sort.Direction.ASC, attr));
                        break;
                    case DESC:
                        orders.add(new Sort.Order(Sort.Direction.DESC, attr));
                        break;
                    default:
                        log.error("wrong sort = {}", sortParam.getDirection());
                        return null;
                }
            }
            Sort sort = Sort.by(orders);
            return PageRequest.of(page, size, sort);
        }
        return null;
    }


    public static <T> Specification<T> mapToSpecification(Map<String, Object> criteria) {
        if (!criteria.containsKey("status")) {
            criteria.put("status", 0);
        }
        Specification<T> specification = (Specification<T>) (root, query, criteriaBuilder) -> {
            Predicate predicate = null;
            for (String key : criteria.keySet()) {
                Object value = criteria.get(key);
                if (null == predicate) {
                    predicate = criteriaBuilder.equal(root.get(key), value);
                } else {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(key), value));
                }
            }
            return predicate;
        };
        return specification;
    }

    public static String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
        return simpleDateFormat.format(date);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void nonNullCopyProperties(Object src, Object dest) {
        BeanUtils.copyProperties(src, dest, getNullPropertyNames(src));
    }


    public static String toJSON(Object obj) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 将json形式字符串转换为java实体类
     */
    public static <T> T parse(String jsonStr, Class<T> clazz) {
        ObjectMapper om = new ObjectMapper();
        T readValue = null;
        try {
            readValue = om.readValue(jsonStr, clazz);
        } catch (JsonParseException e) {
            log.error("", e);
        } catch (JsonMappingException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
        return readValue;
    }

    public static InputStream byteToStream(byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return inputStream;
    }

    public static Map<String, Object> toMap(JQGridParam param) {
        PageParam pageParam = new PageParam();
        List<SortParam> sortParamList = new ArrayList<>();
        pageParam.setSorts(sortParamList);
        pageParam.setSize(param.getRows());
        pageParam.setPage(param.getPage() - 1);
        if (param.getSidx() != null && !param.getSidx().isEmpty() && param.getSord() != null && !param.getSord().isEmpty()) {
            String sort = param.getSidx() + " " + param.getSord();
            String[] sortArray = sort.split(",");
            for (String sortPair : sortArray) {
                sortPair = sortPair.trim();
                String[] attrOrd = sortPair.split(" ");
                SortParam sortParam = new SortParam(attrOrd[0], attrOrd[1]);
                sortParamList.add(sortParam);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("page", pageParam);
        if ("true".equals(param.getSearch())) {
            String filers = param.getFilters();
            JQGridFilterParam jqGridFilterParam = Utils.parse(filers, JQGridFilterParam.class);
            List<JQGridRule> jqGridRules = jqGridFilterParam.getRules();
            for (JQGridRule jqGridRule : jqGridRules) {
                if (jqGridRule.getOp().equals("lt")) {
                    map.put("lessYear",jqGridRule.getData());
                } else {
                    map.put(jqGridRule.getField(), jqGridRule.getData());
                }
            }
        }
        Map<String, Object> others = param.getOthers();
        if (others != null && !others.isEmpty()) {
            map.putAll(others);
        }
        return map;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String sha256Password(String password, String salt) {
        String originalString = password + salt;
        String sha256hex = Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
        return sha256hex;
    }

}
