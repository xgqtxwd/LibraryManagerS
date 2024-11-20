<template>
  <div>
    <h1>公告栏</h1>
    <el-table :data="announcements" style="width: 100%">
      <el-table-column prop="title" label="标题" width="180"></el-table-column>
      <el-table-column prop="content" label="内容"></el-table-column>
      <el-table-column prop="publishDate" label="发布时间" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.publishDate) }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAnnouncements } from '@/api/announcement.js';

const announcements = ref([]);

const fetchAnnouncements = async () => {
  try {
    const response = await getAnnouncements();
    announcements.value = response.data;
  } catch (error) {
    console.error('获取公告失败:', error);
  }
};

// 格式化日期
const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
  return new Date(dateString).toLocaleDateString('zh-CN', options);
};

onMounted(() => {
  fetchAnnouncements();
});
</script>

<style scoped>
h1 {
  text-align: center;
  margin-bottom: 20px;
}

.el-table {
  margin: 0 auto;
  width: 80%;
}

.el-table th, .el-table td {
  text-align: center;
}

.el-table-column {
  padding: 10px;
}
</style> 