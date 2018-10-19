package com.bill99.mam.platform.persistence.dao;

import com.bill99.mam.platform.persistence.entity.SubRefund;
import com.bill99.mam.platform.persistence.entity.SubRefundExample;

import java.util.List;

public interface SubRefundMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_sub_refund
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long subRefundNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_sub_refund
     *
     * @mbg.generated
     */
    int insert(SubRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_sub_refund
     *
     * @mbg.generated
     */
    int insertSelective(SubRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_sub_refund
     *
     * @mbg.generated
     */
    List<SubRefund> selectByExample(SubRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_sub_refund
     *
     * @mbg.generated
     */
    SubRefund selectByPrimaryKey(Long subRefundNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_sub_refund
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SubRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_mock_sub_refund
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SubRefund record);
}