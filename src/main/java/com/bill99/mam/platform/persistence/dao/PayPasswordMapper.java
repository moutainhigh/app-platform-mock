package com.bill99.mam.platform.persistence.dao;

import com.bill99.mam.platform.persistence.entity.PayPassword;
import com.bill99.mam.platform.persistence.entity.PayPasswordExample;

import java.util.List;

public interface PayPasswordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_pay_password
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_pay_password
     *
     * @mbg.generated
     */
    int insert(PayPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_pay_password
     *
     * @mbg.generated
     */
    int insertSelective(PayPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_pay_password
     *
     * @mbg.generated
     */
    List<PayPassword> selectByExample(PayPasswordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_pay_password
     *
     * @mbg.generated
     */
    PayPassword selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_pay_password
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PayPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_pay_password
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PayPassword record);
}