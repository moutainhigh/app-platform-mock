package com.bill99.mam.platform.controller.web.modelattribute;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
@Data
@ToString(exclude = "loginPwd")
public class EnterpriseRegisterInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**客户名称 */
    @NotBlank
    private String name;
    /**标识类型 */
    @NotBlank
    private String idType;
    /**商户标识 */
    @NotBlank
    private String idContent;
    /**公司类型 */
    @NotBlank
    private String companyType;
    /**省份 */
    @NotBlank
    private String province;
    /**城市 */
    @NotBlank
    private String city;
    /**营业执照类型 */
    @NotBlank
    private String businessType;
    /**营业执照注册号 */
    @NotBlank
    private String businessRegno;
    /**经营地址 */
    @NotBlank
    private String address;
    /**MCC（一二级行业） */
    @NotBlank
    private String mccCode;
    /**约定业务 */
    private String constraintBusiness;
    /**公司固话 */
    private String telephone;
    /**成立日期 */
    @NotBlank
    private String registDate;
    /**注册资金 */
    @NotBlank
    private String registFund;
    /**公司邮箱 */
    @NotBlank
    private String email;
    /**法人证件类型 */
    @NotBlank
    private String legalIdCardType;
    /**法人姓名 */
    @NotBlank
    private String legalName;
    /**法人证件号码 */
    @NotBlank
    private String legalIdCardNumber;
    /**法人手机 */
    @NotBlank
    private String legalMobile;
    /**联系人列表 */
    @NotBlank
    private String contactList;
    /**资质列表 */
    @NotBlank
    private String fileList;
    /**产品列表 */
    @NotBlank
    private String productList;
    /**门店列表 */
    private String storeList;
}
