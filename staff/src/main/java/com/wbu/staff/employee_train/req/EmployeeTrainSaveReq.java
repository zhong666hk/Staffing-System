package com.wbu.staff.employee_train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeTrainSaveReq {

    /**
     *
     */
    private Long id;

    /**
     * 员工编号
     */
    private Long eid;

    /**
     * 培训开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date trainStartDate;

    /**
     * 培训结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date trainFinishDate;

    /**
     * 培训内容
     */
    private String trainContent;

    /**
     * 培训状态
     */
    private Integer trainStatus;

    /**
     * 备注
     */
    private String remark;

}
