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

    <h2>最受欢迎书籍排行</h2>
    <div ref="bookChart" style="width: 100%; height: 400px;"></div>

    <h2>借书用户排行</h2>
    <div ref="userChart" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import { getAnnouncements } from '@/api/announcement.js';
import { getPopularBooks, getTopUsers } from '@/api/visualization.js';

const announcements = ref([]);
const bookChart = ref([]);
const userChart = ref([]);
import {getOverdueRecordsByUserId} from '@/api/borrow.js';
import { ElNotification } from 'element-plus';
//检查逾期记录
const checkOverdueRecords = async () => {
  try {
    const response = await getOverdueRecordsByUserId();
    const overdueRecords = response.data;
    if (overdueRecords.length > 0) {
      ElNotification({
        title: '逾期提醒',
        message: `您有${overdueRecords.length}条借阅记录已逾期，请尽快归还。`,
        type: 'warning',
        offset: 100,
      });
    }
  } catch (error) {
    console.error('检查逾期记录失败:', error);
  }
};
checkOverdueRecords();
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

// 初始化图表
const initCharts = async () => {
  try {
    const bookChartInstance = echarts.init(bookChart.value);
    const userChartInstance = echarts.init(userChart.value);

    const bookData = await getPopularBooks();
    const userData = await getTopUsers();

    const bookOption = {
      title: { text: '最受欢迎书籍排行' },
      tooltip: {},
      xAxis: { type: 'category', data: bookData.data.map(item => item.title) },
      yAxis: { type: 'value' },
      series: [{ name: '借阅次数', type: 'bar', data: bookData.data.map(item => item.borrowCount) }]
    };

    const userOption = {
      title: { text: '借书用户排行' },
      tooltip: {},
      xAxis: { type: 'category', data: userData.data.map(item => item.username) },
      yAxis: { type: 'value' },
      series: [{ name: '借阅次数', type: 'bar', data: userData.data.map(item => item.borrowCount) }]
    };

    bookChartInstance.setOption(bookOption);
    userChartInstance.setOption(userOption);
  } catch (error) {
    console.error('图表初始化失败:', error);
  }
};
onMounted(() => {
  fetchAnnouncements();
  initCharts();
});
</script>

<style scoped>
h1, h2 {
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