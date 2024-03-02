<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="messageNotifications"
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
    <a-form :model="messageNotification" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="消息id">
        <a-input v-model:value="messageNotification.mid" />
      </a-form-item>
      <a-form-item label="0表示群发消息">
        <a-input v-model:value="messageNotification.type" />
      </a-form-item>
      <a-form-item label="接受者">
        <a-input v-model:value="messageNotification.acceptId" />
      </a-form-item>
      <a-form-item label="发送者">
        <a-input v-model:value="messageNotification.sendId" />
      </a-form-item>
      <a-form-item label="0 未读 1 已读">
        <a-input v-model:value="messageNotification.state" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";

export default defineComponent({
  name: "messageNotification-view",
  setup() {
    const visible = ref(false);
    let messageNotification = ref({
      id: undefined,
      mid: undefined,
      type: undefined,
      acceptId: undefined,
      sendId: undefined,
      state: undefined,
    });
    const messageNotifications = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
    {
      title: '消息id',
      dataIndex: 'mid',
      key: 'mid',
    },
    {
      title: '0表示群发消息',
      dataIndex: 'type',
      key: 'type',
    },
    {
      title: '接受者',
      dataIndex: 'acceptId',
      key: 'acceptId',
    },
    {
      title: '发送者',
      dataIndex: 'sendId',
      key: 'sendId',
    },
    {
      title: '0 未读 1 已读',
      dataIndex: 'state',
      key: 'state',
    },
    {
      title: '操作',
      dataIndex: 'operation'
    }
    ];

    const onAdd = () => {
      messageNotification.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      messageNotification.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/messageNotification/admin/messageNotification/delete/" + record.id).then((response) => {
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
      axios.post("/messageNotification/admin/messageNotification/save", messageNotification.value).then((response) => {
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
      axios.get("/messageNotification/admin/messageNotification/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        if (response.code===200) {
          messageNotifications.value = response.data.records;
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
      messageNotification,
      visible,
      messageNotifications,
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
