<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="employeeTrains"
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
    <a-form :model="employeeTrain" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="员工编号">
        <a-input v-model:value="employeeTrain.eid" />
      </a-form-item>
      <a-form-item label="培训开始日期">
        <a-date-picker v-model:value="employeeTrain.trainStartDate" valueFormat="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="培训结束日期">
        <a-date-picker v-model:value="employeeTrain.trainFinishDate" valueFormat="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="培训内容">
        <a-input v-model:value="employeeTrain.trainContent" />
      </a-form-item>
      <a-form-item label="培训状态">
        <a-input v-model:value="employeeTrain.trainStatus" />
      </a-form-item>
      <a-form-item label="备注">
        <a-input v-model:value="employeeTrain.remark" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";

export default defineComponent({
  name: "employeeTrain-view",
  setup() {
    const visible = ref(false);
    let employeeTrain = ref({
      id: undefined,
      eid: undefined,
      trainStartDate: undefined,
      trainFinishDate: undefined,
      trainContent: undefined,
      trainStatus: undefined,
      remark: undefined,
    });
    const employeeTrains = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
    {
      title: '员工编号',
      dataIndex: 'eid',
      key: 'eid',
    },
    {
      title: '培训开始日期',
      dataIndex: 'trainStartDate',
      key: 'trainStartDate',
    },
    {
      title: '培训结束日期',
      dataIndex: 'trainFinishDate',
      key: 'trainFinishDate',
    },
    {
      title: '培训内容',
      dataIndex: 'trainContent',
      key: 'trainContent',
    },
    {
      title: '培训状态',
      dataIndex: 'trainStatus',
      key: 'trainStatus',
    },
    {
      title: '备注',
      dataIndex: 'remark',
      key: 'remark',
    },
    {
      title: '操作',
      dataIndex: 'operation'
    }
    ];

    const onAdd = () => {
      employeeTrain.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      employeeTrain.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/employeeTrain/admin/employeeTrain/delete/" + record.id).then((response) => {
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
      axios.post("/employeeTrain/admin/employeeTrain/save", employeeTrain.value).then((response) => {
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
      axios.get("/employeeTrain/admin/employeeTrain/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        if (response.code===200) {
          employeeTrains.value = response.data.records;
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
      employeeTrain,
      visible,
      employeeTrains,
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
