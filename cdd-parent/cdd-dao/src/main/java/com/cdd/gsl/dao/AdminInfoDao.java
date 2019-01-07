package com.cdd.gsl.dao;

import com.cdd.gsl.vo.MenuInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminInfoDao {
    @Select("select password from t_admin_info where account=#{username} and status=1")
   public String selectPasswordByUsername(String username);

    @Select("select r.role_code from t_role_info r " +
            "left join t_admin_info a on r.id=a.role_id left " +
            "join t_role_menu_mapping rm on r.id=rm.role_id " +
            "where a.account=#{username} and a.status=1")
    public String selectRoleCodeByUsername(String username);

    @Select("select m.id as menuId,m.menu_name as menuName,m.menu_code as menuCode,m.parent_id as parentId " +
            "from t_role_info r " +
            "left join t_admin_info a on r.id=a.role_id left " +
            "join t_role_menu_mapping rm on r.id=rm.role_id " +
            "left join t_menu_info m on rm.menu_id=m.id " +
            "where a.account=#{username} and a.status=1")
    public List<MenuInfoVo> selectMenuListByUsername(String username);
}
