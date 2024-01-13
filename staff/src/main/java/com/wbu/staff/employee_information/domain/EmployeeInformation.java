package com.wbu.staff.employee_information.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName employee_information
 */
@TableName(value = "employee_information")
@Data
public class EmployeeInformation implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id编号
     */
    @TableId
    private Long id;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 民族
     */
    private Integer nationId;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 所属部门
     */
    private Integer departmentId;
    /**
     * 职称ID
     */
    private Integer jobLevelId;
    /**
     * 职位ID
     */
    private Integer posId;
    /**
     * 最高学历
     */
    private Object tiptopDegree;
    /**
     * 所属专业
     */
    private String specialty;
    /**
     * 毕业院校
     */
    private String school;
    /**
     * 入职日期
     */
    private Date beginDate;
    /**
     * 在职状态
     */
    private Object workState;
    /**
     * 工号
     */
    private String workId;
    /**
     * 合同期限
     */
    private Double contractTerm;
    /**
     * 转正日期
     */
    private Date conversionTime;
    /**
     * 离职日期
     */
    private Date notWorkDate;
    /**
     * 合同起始日期
     */
    private Date beginContract;
    /**
     * 合同终止日期
     */
    private Date endContract;
    /**
     * 工龄
     */
    private Integer workAge;
    /**
     * 员工id
     */
    private Long employeeId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        EmployeeInformation other = (EmployeeInformation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
                && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
                && (this.getIdCard() == null ? other.getIdCard() == null : this.getIdCard().equals(other.getIdCard()))
                && (this.getNationId() == null ? other.getNationId() == null : this.getNationId().equals(other.getNationId()))
                && (this.getNativePlace() == null ? other.getNativePlace() == null : this.getNativePlace().equals(other.getNativePlace()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
                && (this.getJobLevelId() == null ? other.getJobLevelId() == null : this.getJobLevelId().equals(other.getJobLevelId()))
                && (this.getPosId() == null ? other.getPosId() == null : this.getPosId().equals(other.getPosId()))
                && (this.getTiptopDegree() == null ? other.getTiptopDegree() == null : this.getTiptopDegree().equals(other.getTiptopDegree()))
                && (this.getSpecialty() == null ? other.getSpecialty() == null : this.getSpecialty().equals(other.getSpecialty()))
                && (this.getSchool() == null ? other.getSchool() == null : this.getSchool().equals(other.getSchool()))
                && (this.getBeginDate() == null ? other.getBeginDate() == null : this.getBeginDate().equals(other.getBeginDate()))
                && (this.getWorkState() == null ? other.getWorkState() == null : this.getWorkState().equals(other.getWorkState()))
                && (this.getWorkId() == null ? other.getWorkId() == null : this.getWorkId().equals(other.getWorkId()))
                && (this.getContractTerm() == null ? other.getContractTerm() == null : this.getContractTerm().equals(other.getContractTerm()))
                && (this.getConversionTime() == null ? other.getConversionTime() == null : this.getConversionTime().equals(other.getConversionTime()))
                && (this.getNotWorkDate() == null ? other.getNotWorkDate() == null : this.getNotWorkDate().equals(other.getNotWorkDate()))
                && (this.getBeginContract() == null ? other.getBeginContract() == null : this.getBeginContract().equals(other.getBeginContract()))
                && (this.getEndContract() == null ? other.getEndContract() == null : this.getEndContract().equals(other.getEndContract()))
                && (this.getWorkAge() == null ? other.getWorkAge() == null : this.getWorkAge().equals(other.getWorkAge()))
                && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getIdCard() == null) ? 0 : getIdCard().hashCode());
        result = prime * result + ((getNationId() == null) ? 0 : getNationId().hashCode());
        result = prime * result + ((getNativePlace() == null) ? 0 : getNativePlace().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getJobLevelId() == null) ? 0 : getJobLevelId().hashCode());
        result = prime * result + ((getPosId() == null) ? 0 : getPosId().hashCode());
        result = prime * result + ((getTiptopDegree() == null) ? 0 : getTiptopDegree().hashCode());
        result = prime * result + ((getSpecialty() == null) ? 0 : getSpecialty().hashCode());
        result = prime * result + ((getSchool() == null) ? 0 : getSchool().hashCode());
        result = prime * result + ((getBeginDate() == null) ? 0 : getBeginDate().hashCode());
        result = prime * result + ((getWorkState() == null) ? 0 : getWorkState().hashCode());
        result = prime * result + ((getWorkId() == null) ? 0 : getWorkId().hashCode());
        result = prime * result + ((getContractTerm() == null) ? 0 : getContractTerm().hashCode());
        result = prime * result + ((getConversionTime() == null) ? 0 : getConversionTime().hashCode());
        result = prime * result + ((getNotWorkDate() == null) ? 0 : getNotWorkDate().hashCode());
        result = prime * result + ((getBeginContract() == null) ? 0 : getBeginContract().hashCode());
        result = prime * result + ((getEndContract() == null) ? 0 : getEndContract().hashCode());
        result = prime * result + ((getWorkAge() == null) ? 0 : getWorkAge().hashCode());
        result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", idCard=").append(idCard);
        sb.append(", nationId=").append(nationId);
        sb.append(", nativePlace=").append(nativePlace);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", address=").append(address);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", jobLevelId=").append(jobLevelId);
        sb.append(", posId=").append(posId);
        sb.append(", tiptopDegree=").append(tiptopDegree);
        sb.append(", specialty=").append(specialty);
        sb.append(", school=").append(school);
        sb.append(", beginDate=").append(beginDate);
        sb.append(", workState=").append(workState);
        sb.append(", workId=").append(workId);
        sb.append(", contractTerm=").append(contractTerm);
        sb.append(", conversionTime=").append(conversionTime);
        sb.append(", notWorkDate=").append(notWorkDate);
        sb.append(", beginContract=").append(beginContract);
        sb.append(", endContract=").append(endContract);
        sb.append(", workAge=").append(workAge);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}