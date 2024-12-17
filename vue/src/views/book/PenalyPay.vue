<script setup>
import { ref } from 'vue'
import {ElMessage, ElMessageBox} from "element-plus"
import {bookReturnListService, bookReturnService, borrowAgainService, penaltyListService} from "@/api/book.js"

// 控制抽屉是否显示
const visibleDrawer = ref(false)
const visibleDrawer2 = ref(false)

// 添加表单数据模型
const overdueModel = ref({
  id: '',
  title: '',
  writer: '',
  coverImg: '',
  endTime: '',
  borrowTime: '',
  penalty:'',
})

// 用户输入的图书名和作者名
const bookTitle = ref('')
const authorName = ref('')

// 文章列表数据模型
const articles = ref([])

// 分页条数据模型
const pageNum = ref(1)
const total = ref(20)
const pageSize = ref(3)

// 当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
  bookReturnList(); // 重新加载数据
}

// 当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
  bookReturnList(); // 重新加载数据
}





// 搜索函数
const bookReturnList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    title: bookTitle.value ? bookTitle.value : null,
    writer: authorName.value ? authorName.value : null
  }

  let result = await  penaltyListService(params);
  total.value = result.data.total
  articles.value = result.data;
  console.log(result.data);
}
bookReturnList();


// 重置搜索条件
const resetSearch = () => {
  bookTitle.value = ''
  authorName.value = ''
}


// 格式化日期
const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString('zh-CN', options);
};

// 判断是否逾期
const isOverdue = (endTime) => {
  return new Date(endTime) < new Date();
};
//支付罚款
const payPenalty = async (row) => {
  window.open('http://localhost:8080/alipay/pay?overdueId=' + row.overdueId);
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>逾期处罚</span>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="图书名：">
        <el-input v-model="bookTitle" placeholder="请输入图书名"></el-input>
      </el-form-item>
      <el-form-item label="作者名：">
        <el-input v-model="authorName" placeholder="请输入作者名"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="bookReturnList()">搜索</el-button>
        <el-button @click="resetSearch()">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 文章列表 -->
    <el-table :data="articles" style="width: 100%">
      <el-table-column label="序号" width="100" prop="overdueId" type="index"></el-table-column>
      <el-table-column label="图书名" width="200" prop="title">
        <template #default="{ row }">
          <span>{{ row.title }}</span>
          <el-tag v-if="isOverdue(row.endTime)" type="danger">已逾期</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="封面" prop="coverImg">
        <template #default="{ row }">
          <img :src="row.coverImg" alt="封面" style="width: 200px; height: auto;">
        </template>
      </el-table-column>
      <el-table-column label="作者" width="200" prop="writer"></el-table-column>
      <el-table-column label="借阅时间" width="200" prop="borrowingTime">
        <template #default="scope">
          {{ formatDate(scope.row.borrowingTime) }}
        </template>
      </el-table-column>
      <el-table-column label="归还期限" width="200" prop="endTime">
        <template #default="scope">
          {{ formatDate(scope.row.endTime) }}
        </template>
      </el-table-column>
      <el-table-column label="罚款金额" width="200" prop="penalty">
        <template #default="{ row }">
          <span>{{ row.penalty }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button type="success" plain @click="payPenalty(row)">支付罚金</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->

    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5 ,10, 15]"
                   layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
                   @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
  </el-card>

</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>