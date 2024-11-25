<template>
  <div>
    <h1>逾期提醒</h1>
    <el-table :data="overdueRecords" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="userId" label="用户ID" width="180"></el-table-column>
      <el-table-column prop="bookId" label="图书ID"></el-table-column>
      <el-table-column prop="borrowTime" label="借阅日期" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.borrowTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="到期日期" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.endTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" @click="sendReminder(row)">发送提醒</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getOverdueRecords, sendReminder as apiSendReminder } from '@/api/borrow.js';
import { ElMessage } from 'element-plus';

// 格式化日期
const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString('zh-CN', options);
};

const overdueRecords = ref([]);

const fetchOverdueRecords = async () => {
  try {
    const response = await getOverdueRecords();
    overdueRecords.value = response.data;
  } catch (error) {
    console.error('获取逾期记录失败:', error);
    ElMessage.error('获取逾期记录失败');
  }
};

const sendReminder = async (record) => {
  try {
    await apiSendReminder({ userId: record.userId, bookId: record.bookId });
    ElMessage.success(`已向用户${record.userId}发送提醒`);
  } catch (error) {
    console.error('发送提醒失败:', error);
    ElMessage.error('发送提醒失败');
  }
};

onMounted(() => {
  fetchOverdueRecords();
});
</script>

<style scoped>
h1 {
  text-align: center;
  margin-bottom: 20px;
}
</style> 