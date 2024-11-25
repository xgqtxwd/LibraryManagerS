<template>
  <div>
    <h1>用户管理</h1>
    <el-button type="primary" @click="showAddDialog">添加用户</el-button>
    <el-table :data="users" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="username" label="用户名" width="180"></el-table-column>
      <el-table-column prop="nickname" label="昵称"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="privilegeLevel" label="权限等级" width="100">
        <template #default="scope">
          {{ scope.row.privilegeLevel === 1 ? '管理员' : '普通用户' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280">
        <template #default="{ row }">
          <el-button type="primary" @click="editUser(row)">编辑</el-button>
          <el-button type="warning" @click="showChangePasswordDialog(row)">修改密码</el-button>
          <el-button type="danger" @click="handleDeleteUser(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑用户对话框 -->
    <el-dialog v-model="dialogVisible" title="用户">
      <el-form :model="currentUser">
        <el-form-item label="用户名">
          <el-input v-model="currentUser.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="currentUser.nickname"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="currentUser.email"></el-input>
        </el-form-item>
        <el-form-item label="权限等级">
          <el-select v-model="currentUser.privilegeLevel" placeholder="请选择权限等级">
            <el-option label="管理员" :value="1"></el-option>
            <el-option label="普通用户" :value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
      </template>
    </el-dialog>

    <!-- 修改密码对话框 -->
    <el-dialog v-model="passwordDialogVisible" title="修改密码">
      <el-form :model="passwordForm">
        <el-form-item label="新密码">
          <el-input type="password" v-model="passwordForm.newPassword"></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input type="password" v-model="passwordForm.confirmPassword"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="passwordDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="changePassword">修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getUsers, addUser, updateUser, deleteUser, changeUserPassword } from '@/api/user.js';
import { ElMessage } from 'element-plus';

const users = ref([]);
const dialogVisible = ref(false);
const passwordDialogVisible = ref(false);
const currentUser = ref({ id: null, username: '', nickname: '', email: '', privilegeLevel: 0 });
const passwordForm = ref({ newPassword: '', confirmPassword: '' });

const fetchUsers = async () => {
  try {
    const response = await getUsers();
    users.value = response.data;
  } catch (error) {
    console.error('获取用户失败:', error);
    ElMessage.error('获取用户失败');
  }
};

const showAddDialog = () => {
  currentUser.value = { id: null, username: '', nickname: '', email: '', privilegeLevel: 0 };
  dialogVisible.value = true;
};

const editUser = (user) => {
  currentUser.value = { ...user };
  dialogVisible.value = true;
};

const saveUser = async () => {
  try {
    if (currentUser.value.id) {
      await updateUser(currentUser.value);
      ElMessage.success('用户更新成功');
    } else {
      await addUser(currentUser.value);
      ElMessage.success('用户添加成功');
    }
    dialogVisible.value = false;
    fetchUsers();
  } catch (error) {
    console.error('保存用户失败:', error);
    ElMessage.error('保存用户失败');
  }
};

const handleDeleteUser = async (id) => {
  try {
    await deleteUser(id);
    ElMessage.success('用户删除成功');
    fetchUsers();
  } catch (error) {
    console.error('删除用户失败:', error);
    ElMessage.error('删除用户失败');
  }
};

const showChangePasswordDialog = (user) => {
  currentUser.value = { ...user };
  passwordForm.value = { newPassword: '', confirmPassword: '' };
  passwordDialogVisible.value = true;
};

const changePassword = async () => {
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致');
    return;
  }
  try {
    await changeUserPassword(currentUser.value.id, passwordForm.value.newPassword);
    ElMessage.success('密码修改成功');
    passwordDialogVisible.value = false;
  } catch (error) {
    console.error('修改密码失败:', error);
    ElMessage.error('修改密码失败');
  }
};

onMounted(() => {
  fetchUsers();
});
</script>

<style scoped>
h1 {
  text-align: center;
  margin-bottom: 20px;
}
</style>