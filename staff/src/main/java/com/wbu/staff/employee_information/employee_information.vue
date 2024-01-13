<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="employeeInformations"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'operation'">
        <a-space>
          <a-popconfirm
              title="删除后不可恢复，确认删除?"
              @confirm="onDelete(record)"
              ok-text="确认" cancel-text="取消">
            <a style="color: red">删除</a>
          </a-popconfirm>
          <a @click="onEdit(record)">编辑</a>
        </a-space>
      </template>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="employeeInformation" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="员工姓名">
        <a-input v-model:value="employeeInformation.name" />
      </a-form-item>
      <a-form-item label="性别">
        <a-input v-model:value="employeeInformation.gender" />
      </a-form-item>
      <a-form-item label="出生日期">
        <a-date-picker v-model:value="employeeInformation.birthday" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="身份证号">
        <a-input v-model:value="employeeInformation.idCard" />
      </a-form-item>
      <a-form-item label="民族">
        <a-input v-model:value="employeeInformation.nationId" />
      </a-form-item>
      <a-form-item label="籍贯">
        <a-input v-model:value="employeeInformation.nativePlace" />
      </a-form-item>
      <a-form-item label="邮箱">
        <a-input v-model:value="employeeInformation.email" />
      </a-form-item>
      <a-form-item label="电话号码">
        <a-input v-model:value="employeeInformation.phone" />
      </a-form-item>
      <a-form-item label="联系地址">
        <a-input v-model:value="employeeInformation.address" />
      </a-form-item>
      <a-form-item label="所属部门">
        <a-input v-model:value="employeeInformation.departmentId" />
      </a-form-item>
      <a-form-item label="职称ID">
        <a-input v-model:value="employeeInformation.jobLevelId" />
      </a-form-item>
      <a-form-item label="职位ID">
        <a-input v-model:value="employeeInformation.posId" />
      </a-form-item>
      <a-form-item label="最高学历">
        <a-input v-model:value="employeeInformation.tiptopDegree" />
      </a-form-item>
      <a-form-item label="所属专业">
        <a-input v-model:value="employeeInformation.specialty" />
      </a-form-item>
      <a-form-item label="毕业院校">
        <a-input v-model:value="employeeInformation.school" />
      </a-form-item>
      <a-form-item label="入职日期">
        <a-date-picker v-model:value="employeeInformation.beginDate" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="在职状态">
        <a-input v-model:value="employeeInformation.workState" />
      </a-form-item>
      <a-form-item label="工号">
        <a-input v-model:value="employeeInformation.workId" />
      </a-form-item>
      <a-form-item label="合同期限">
        <a-input v-model:value="employeeInformation.contractTerm" />
      </a-form-item>
      <a-form-item label="转正日期">
        <a-date-picker v-model:value="employeeInformation.conversionTime" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="离职日期">
        <a-date-picker v-model:value="employeeInformation.notWorkDate" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="合同起始日期">
        <a-date-picker v-model:value="employeeInformation.beginContract" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="合同终止日期">
        <a-date-picker v-model:value="employeeInformation.endContract" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="工龄">
        <a-input v-model:value="employeeInformation.workAge" />
      </a-form-item>
      <a-form-item label="员工id">
        <a-input v-model:value="employeeInformation.employeeId" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";

export default defineComponent({
  name: "employeeInformation-view",
  setup() {
    const visible = ref(false);
    let employeeInformation = ref({
      id: undefined,
      name: undefined,
      gender: undefined,
      birthday: undefined,
      idCard: undefined,
      nationId: undefined,
      nativePlace: undefined,
      email: undefined,
      phone: undefined,
      address: undefined,
      departmentId: undefined,
      jobLevelId: undefined,
      posId: undefined,
      tiptopDegree: undefined,
      specialty: undefined,
      school: undefined,
      beginDate: undefined,
      workState: undefined,
      workId: undefined,
      contractTerm: undefined,
      conversionTime: undefined,
      notWorkDate: undefined,
      beginContract: undefined,
      endContract: undefined,
      workAge: undefined,
      employeeId: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const employeeInformations = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
    {
      title: '员工姓名',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '性别',
      dataIndex: 'gender',
      key: 'gender',
    },
    {
      title: '出生日期',
      dataIndex: 'birthday',
      key: 'birthday',
    },
    {
      title: '身份证号',
      dataIndex: 'idCard',
      key: 'idCard',
    },
    {
      title: '民族',
      dataIndex: 'nationId',
      key: 'nationId',
    },
    {
      title: '籍贯',
      dataIndex: 'nativePlace',
      key: 'nativePlace',
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      key: 'email',
    },
    {
      title: '电话号码',
      dataIndex: 'phone',
      key: 'phone',
    },
    {
      title: '联系地址',
      dataIndex: 'address',
      key: 'address',
    },
    {
      title: '所属部门',
      dataIndex: 'departmentId',
      key: 'departmentId',
    },
    {
      title: '职称ID',
      dataIndex: 'jobLevelId',
      key: 'jobLevelId',
    },
    {
      title: '职位ID',
      dataIndex: 'posId',
      key: 'posId',
    },
    {
      title: '最高学历',
      dataIndex: 'tiptopDegree',
      key: 'tiptopDegree',
    },
    {
      title: '所属专业',
      dataIndex: 'specialty',
      key: 'specialty',
    },
    {
      title: '毕业院校',
      dataIndex: 'school',
      key: 'school',
    },
    {
      title: '入职日期',
      dataIndex: 'beginDate',
      key: 'beginDate',
    },
    {
      title: '在职状态',
      dataIndex: 'workState',
      key: 'workState',
    },
    {
      title: '工号',
      dataIndex: 'workId',
      key: 'workId',
    },
    {
      title: '合同期限',
      dataIndex: 'contractTerm',
      key: 'contractTerm',
    },
    {
      title: '转正日期',
      dataIndex: 'conversionTime',
      key: 'conversionTime',
    },
    {
      title: '离职日期',
      dataIndex: 'notWorkDate',
      key: 'notWorkDate',
    },
    {
      title: '合同起始日期',
      dataIndex: 'beginContract',
      key: 'beginContract',
    },
    {
      title: '合同终止日期',
      dataIndex: 'endContract',
      key: 'endContract',
    },
    {
      title: '工龄',
      dataIndex: 'workAge',
      key: 'workAge',
    },
    {
      title: '员工id',
      dataIndex: 'employeeId',
      key: 'employeeId',
    },
    {
      title: '操作',
      dataIndex: 'operation'
    }
    ];

    const onAdd = () => {
      employeeInformation.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      employeeInformation.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/employeeInformation/admin/employeeInformation/delete/" + record.id).then((response) => {
        if (response.code===200) {
          notification.success({description: response.message});
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        } else {
          notification.error({description: response.message});
        }
      });
    };

    const handleOk = () => {
      axios.post("/employeeInformation/admin/employeeInformation/save", employeeInformation.value).then((response) => {
        if (response.code===200) {
          notification.success({description: response.message});
          visible.value = false;
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          notification.error({description: response.message});
        }
      });
    };

    const handleQuery = (param) => {
      if (!param) {
        param = {
          page: 1,
          size: pagination.value.pageSize
        };
      }
      loading.value = true;
      axios.get("/employeeInformation/admin/employeeInformation/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        if (response.code===200) {
          employeeInformations.value = response.data.records;
          // 设置分页控件的值
          pagination.value.current = param.page;
          pagination.value.total = response.data.total;
        } else {
          notification.error({description: response.message});
        }
      });
    };

    const handleTableChange = (page) => {
      // console.log("看看自带的分页参数都有啥：" + JSON.stringify(page));
      pagination.value.pageSize = page.pageSize;
      handleQuery({
        page: page.current,
        size: page.pageSize
      });
    };

    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });

    return {
      employeeInformation,
      visible,
      employeeInformations,
      pagination,
      columns,
      handleTableChange,
      handleQuery,
      loading,
      onAdd,
      handleOk,
      onEdit,
      onDelete
    };
  },
});
</script>
