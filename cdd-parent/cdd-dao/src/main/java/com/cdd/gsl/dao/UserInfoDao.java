package com.cdd.gsl.dao;

import com.cdd.gsl.domain.UserInfoDomain;
import com.cdd.gsl.vo.SingleUserBrokerVo;
import com.cdd.gsl.vo.SingleUserInfoVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select id as userId,username as username,phone as phone,portrait as portrait " +
            "from t_user_info where id=#{userId} and status=1")
    SingleUserInfoVo findUserInfoById(Long userId);
    @Select("<script>select u.id as userId,u.username as username,u.phone as phone,u.portrait as portrait," +
            "b.apply_type as applyType,b.id as applyBrokerId" +
            " from t_user_info u left join t_apply_broker_info b on u.id=b.user_id where b.company_name=" +
            "(select company_name from t_apply_broker_info where apply_type=2 and user_id=#{userId})" +
            "<if test=\"userType == 2\">" +
            " and b.apply_type=2" +
            "</if>" +
            "</script>")
    List<SingleUserBrokerVo> findUserBrokerByUserId(@Param("userId") Long userId, @Param("userType") Integer userType);
}
