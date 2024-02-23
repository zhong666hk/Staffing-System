<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="resumes"
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
  <a-modal v-model:visible="visible" title="简历" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="resume" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="员工姓名">
        <a-input v-model:value="resume.name" />
      </a-form-item>
      <a-form-item label="性别 1为男 2为女">
        <a-input v-model:value="resume.gender" />
      </a-form-item>
      <a-form-item label="出生日期">
        <a-date-picker v-model:value="resume.birthday" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="身份证号">
        <a-input v-model:value="resume.idCard" />
      </a-form-item>
      <a-form-item label="民族">
        <a-input v-model:value="resume.nationId" />
      </a-form-item>
      <a-form-item label="籍贯">
        <a-input v-model:value="resume.nativePlace" />
      </a-form-item>
      <a-form-item label="邮箱">
        <a-input v-model:value="resume.email" />
      </a-form-item>
      <a-form-item label="电话号码">
        <a-input v-model:value="resume.phone" />
      </a-form-item>
      <a-form-item label="联系地址">
        <a-input v-model:value="resume.address" />
      </a-form-item>
      <a-form-item label="所属部门">
        <a-input v-model:value="resume.departmentId" />
      </a-form-item>
      <a-form-item label="职称ID">
        <a-input v-model:value="resume.jobLevelId" />
      </a-form-item>
      <a-form-item label="职位ID">
        <a-input v-model:value="resume.posId" />
      </a-form-item>
      <a-form-item label="最高学历">
        <a-input v-model:value="resume.tiptopDegree" />
      </a-form-item>
      <a-form-item label="所属专业">
        <a-input v-model:value="resume.specialty" />
      </a-form-item>
      <a-form-item label="毕业院校">
        <a-input v-model:value="resume.school" />
      </a-form-item>
      <a-form-item label="职业技能描述">
        <a-input v-model:value="resume.vocationalSkills" />
      </a-form-item>
      <a-form-item label="自我描述">
        <a-input v-model:value="resume.introduction" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";

export default defineComponent({
  name: "resume-view",
  setup() {
    const visible = ref(false);
    let resume = ref({
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
      vocationalSkills: undefined,
      introduction: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const resumes = ref([]);
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
      title: '性别 1为男 2为女',
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
      title: '职业技能描述',
      dataIndex: 'vocationalSkills',
      key: 'vocationalSkills',
    },
    {
      title: '自我描述',
      dataIndex: 'introduction',
      key: 'introduction',
    },
    {
      title: '操作',
      dataIndex: 'operation'
    }
    ];

    const onAdd = () => {
      resume.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      resume.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/resume/admin/resume/delete/" + record.id).then((response) => {
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
      axios.post("/resume/admin/resume/save", resume.value).then((response) => {
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
      axios.get("/resume/admin/resume/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        if (response.code===200) {
          resumes.value = response.data.records;
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
      resume,
      visible,
      resumes,
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
