<template>
  <div>
    <h1>公告管理</h1>
    <el-button type="primary" @click="showAddDialog">添加公告</el-button>
    <el-table :data="announcements" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="title" label="标题" width="180"></el-table-column>
      <el-table-column prop="content" label="内容"></el-table-column>
      <el-table-column prop="publishDate" label="发布日期" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.publishDate) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" @click="editAnnouncement(row)">编辑</el-button>
          <el-button type="danger" @click="handleDeleteAnnouncement(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑公告对话框 -->
    <el-dialog v-model="dialogVisible" title="公告">
      <el-form :model="currentAnnouncement">
        <el-form-item label="标题">
          <el-input v-model="currentAnnouncement.title"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="currentAnnouncement.content"></el-input>
        </el-form-item>
        <el-form-item label="发布日期">
          <el-date-picker v-model="currentAnnouncement.publishDate" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="过期日期">
          <el-date-picker v-model="currentAnnouncement.expirationDate" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="是否有效">
          <el-switch v-model="currentAnnouncement.isActive"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveAnnouncement">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllAnnouncements, addAnnouncement, updateAnnouncement, deleteAnnouncement as apiDeleteAnnouncement } from '@/api/announcement.js';
import { ElMessage } from 'element-plus';

const announcements = ref([]);
const dialogVisible = ref(false);
const currentAnnouncement = ref({ id: null, title: '', content: '', publishDate: '', expirationDate: '', isActive: false });

const fetchAnnouncements = async () => {
  try {
    const response = await getAllAnnouncements();
    announcements.value = response.data;
  } catch (error) {
    console.error('获取公告失败:', error);
    ElMessage.error('获取公告失败');
  }
};

const showAddDialog = () => {
  currentAnnouncement.value = { id: null, title: '', content: '', publishDate: '', expirationDate: '', isActive: false };
  dialogVisible.value = true;
};

const editAnnouncement = (announcement) => {
  currentAnnouncement.value = { ...announcement };
  dialogVisible.value = true;
};

const saveAnnouncement = async () => {
  try {
    if (currentAnnouncement.value.id) {
      await updateAnnouncement(currentAnnouncement.value);
      ElMessage.success('公告更新成功');
    } else {
      await addAnnouncement(currentAnnouncement.value);
      ElMessage.success('公告添加成功');
    }
    dialogVisible.value = false;
    fetchAnnouncements();
  } catch (error) {
    console.error('保存公告失败:', error);
    ElMessage.error('保存公告失败');
  }
};

const handleDeleteAnnouncement = async (id) => {
  try {
    await apiDeleteAnnouncement(id);
    ElMessage.success('公告删除成功');
    fetchAnnouncements();
  } catch (error) {
    console.error('删除公告失败:', error);
    ElMessage.error('删除公告失败');
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
</style>