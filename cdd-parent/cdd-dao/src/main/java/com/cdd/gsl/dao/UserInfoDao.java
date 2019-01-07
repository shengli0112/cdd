package com.cdd.gsl.dao;

import com.cdd.gsl.domain.UserInfoDomain;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

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
}
