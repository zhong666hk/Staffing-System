<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="salarys"
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
    <a-form :model="salary" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="基本工资">
        <a-input v-model:value="salary.basicSalary" />
      </a-form-item>
      <a-form-item label="奖金">
        <a-input v-model:value="salary.bonus" />
      </a-form-item>
      <a-form-item label="午餐补助">
        <a-input v-model:value="salary.lunchSalary" />
      </a-form-item>
      <a-form-item label="交通补助">
        <a-input v-model:value="salary.trafficSalary" />
      </a-form-item>
      <a-form-item label="应发工资">
        <a-input v-model:value="salary.allSalary" />
      </a-form-item>
      <a-form-item label="员工id">
        <a-input v-model:value="salary.employeeId" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";

export default defineComponent({
  name: "salary-view",
  setup() {
    const visible = ref(false);
    let salary = ref({
      id: undefined,
      basicSalary: undefined,
      bonus: undefined,
      lunchSalary: undefined,
      trafficSalary: undefined,
      allSalary: undefined,
      createTime: undefined,
      updateTime: undefined,
      employeeId: undefined,
    });
    const salarys = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
    {
      title: '基本工资',
      dataIndex: 'basicSalary',
      key: 'basicSalary',
    },
    {
      title: '奖金',
      dataIndex: 'bonus',
      key: 'bonus',
    },
    {
      title: '午餐补助',
      dataIndex: 'lunchSalary',
      key: 'lunchSalary',
    },
    {
      title: '交通补助',
      dataIndex: 'trafficSalary',
      key: 'trafficSalary',
    },
    {
      title: '应发工资',
      dataIndex: 'allSalary',
      key: 'allSalary',
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
      salary.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      salary.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/salary/admin/salary/delete/" + record.id).then((response) => {
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
      axios.post("/salary/admin/salary/save", salary.value).then((response) => {
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
      axios.get("/salary/admin/salary/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        if (response.code===200) {
          salarys.value = response.data.records;
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
      salary,
      visible,
      salarys,
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
