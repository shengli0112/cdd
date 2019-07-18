package com.cdd.gsl.dao;

import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.vo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserInfoDao {
    @Insert("insert into t_user_info\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        id,\n" +
            "      </if>\n" +
            "      <if test=\"username != null\" >\n" +
            "        username,\n" +
            "      </if>\n" +
            "      <if test=\"phone != null\" >\n" +
            "        phone,\n" +
            "      </if>\n" +
            "<if test=\"password != null\" >\n" +
            "            password,\n" +
            "            </if>\n" +
            "            <if test=\"salt != null\" >\n" +
            "            salt,\n" +
            "            </if> "+
            "      <if test=\"userType != null\" >\n" +
            "        user_type,\n" +
            "      </if>\n" +
            "      <if test=\"integral != null\" >\n" +
            "        integral,\n" +
            "      </if>\n" +
            "      <if test=\"portrait != null\" >\n" +
            "        portrait,\n" +
            "      </if>\n" +
            "      <if test=\"status != null\" >\n" +
            "        status,\n" +
            "      </if>\n" +
            "      <if test=\"createTs != null\" >\n" +
            "        create_ts,\n" +
            "      </if>\n" +
            "      <if test=\"updateTs != null\" >\n" +
            "        update_ts,\n" +
            "      </if>\n" +
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        #{id,jdbcType=BIGINT},\n" +
            "      </if>\n" +
            "      <if test=\"username != null\" >\n" +
            "        #{username,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"phone != null\" >\n" +
            "        #{phone,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "       <if test=\"password != null\" >\n" +
            "        #{password,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"salt != null\" >\n" +
            "        #{salt,jdbcType=VARCHAR},\n" +
            "      </if>"+
            "      <if test=\"userType != null\" >\n" +
            "        #{userType,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"integral != null\" >\n" +
            "        #{integral,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"portrait != null\" >\n" +
            "        #{portrait,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"status != null\" >\n" +
            "        #{status,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"createTs != null\" >\n" +
            "        #{createTs,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"updateTs != null\" >\n" +
            "        #{updateTs,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "    </trim>")
    @Options(useGeneratedKeys = true,keyProperty = "id" ,keyColumn = "id")
    void insertUserInfo(UserInfoDomain userInfoDomain);

    @Select("select id as userId,username as username,phone as phone,portrait as portrait,service_area as serviceArea " +
            "from t_user_info where id=#{userId} and status=1")
    SingleUserInfoVo findUserInfoById(Long userId);
    @Select("<script>select u.id as userId,u.username as username,u.phone as phone,u.portrait as portrait," +
            "b.apply_type as applyType,b.id as applyBrokerId,u.service_area as serviceArea" +
            " from t_user_info u left join t_apply_broker_info b on u.id=b.user_id where b.company_name=" +
            "(select company_name from t_apply_broker_info where apply_type=2 and user_id=#{userId}) and b.status=1 and u.status=1" +
            "<if test=\"userType == 2\">" +
            " and b.apply_type=2" +
            "</if>" +
            "</script>")
    List<SingleUserBrokerVo> findUserBrokerByUserId(@Param("userId") Long userId, @Param("userType") Integer userType);

    @Select("select count(*) from t_user_info where status=1")
    int selectAllUserCount();

    @Update("update t_user_info set integral=integral+#{integral} where id=#{userId}")
    void updateUserintegralById(@Param("userId")Long userId,@Param("integral")Integer integral);

    @Select("<script> select id as id,username as username,phone as phone,user_type as userType,integral as integral,portrait as portrait," +
            "status as status,create_ts as createTs,update_ts as updateTs,service_area as serviceArea" +
            " from t_user_info" +
            " where 1=1 " +
            "<if test='keyword != null'>" +
            " and (username like concat('%','${keyword}','%') or phone like concat('%','${keyword}','%')) " +
            "</if>"+
            " order by status desc,id asc "+
            " limit #{from},#{limit} </script>")
    List<UserInfoDemainVo> userList(UserAdminConditionVo userConditionVo);

    @Select("<script> select count(*)" +
            " from t_user_info" +
            " where 1=1 " +
            "<if test='keyword != null'>" +
            " and (username like concat('%','${keyword}','%')) " +
            "</if> </script>")
    int userCount(UserAdminConditionVo userConditionVo);
}
