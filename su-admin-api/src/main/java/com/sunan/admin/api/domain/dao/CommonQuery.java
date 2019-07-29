package com.sunan.admin.api.domain.dao;

import com.sunan.admin.api.common.AppConfig;
import com.sunan.admin.api.common.MyPage;
import com.sunan.admin.api.common.PageRequest;
import com.sunan.admin.api.common.RetVal;
import com.sunan.admin.api.common.enums.RetFlag;
import com.sunan.admin.api.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonQuery<T> {
    @Autowired
    private CommonDao commonDao;

    public RetVal<MyPage<T>> getListByPageWithRetVal(StringBuilder sql_sb, Map<String, Object> params, PageRequest pageRequest, Class<T> clazz) {
        RetVal<MyPage<T>> retVal = new RetVal<>(RetFlag.Success, null, getListByPage(sql_sb, params, pageRequest, clazz));
        return retVal;
    }

    public MyPage<T> getListByPage(StringBuilder sql_sb, Map<String, Object> params, PageRequest pageRequest, Class<T> clazz) {
        Integer nowPage = 1;
        Integer size = AppConfig.DefaultPageSize;
        if (pageRequest != null) {
            nowPage = pageRequest.getNowPage() == null ? nowPage : pageRequest.getNowPage();
            size = pageRequest.getPageSize() == null ? size : pageRequest.getPageSize();
        }
        int totalElement = commonDao.getCountBy(CommonUtils.getCountSql(sql_sb), params);
        List<T> list = (List<T>) commonDao.queryListEntity(CommonUtils.getPageSql(sql_sb, nowPage, size), params, clazz);
        MyPage<T> myPage = new MyPage<>(nowPage, totalElement, size, list);
        return myPage;
    }

    public Integer deleteOrUpDate(String sql, Map<String, Object> params) {
        return commonDao.deleteOrUpDate(sql, params);
    }

    public Integer getCountBy(String sql, Map<String, Object> params) {
        return commonDao.getCountBy(sql, params);
    }

    public List<T> queryListEntity(StringBuilder sql_sb, Map<String, Object> params, Class<T> clazz) {
        return (List<T>) commonDao.queryListEntity(sql_sb.toString(), params, clazz);
    }

    public RetVal<List<T>> queryListEntityWithRetVal(StringBuilder sql_sb, Map<String, Object> params, Class<T> clazz) {
        RetVal<List<T>> retVal = new RetVal<>(RetFlag.Success, null, queryListEntity(sql_sb, params, clazz));
        return retVal;
    }
}
