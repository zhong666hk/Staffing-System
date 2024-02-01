package com.wbu.staff.nation.resp;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NationQueryResp {

    /**
     * 
     */
    private Integer id;

    /**
     * 名族名称
     */
    @NotBlank(message = "【名族名称】不能为空")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}
