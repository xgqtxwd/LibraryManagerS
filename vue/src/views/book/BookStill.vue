<script setup>
import { ref } from 'vue'
import { Edit, Delete } from '@element-plus/icons-vue'
import {
  bookListService,
  bookBorrowService,
  bookUpdateService,
  bookReturnService,
  bookReturnListService
} from "@/api/book.js";
import {ElMessage} from "element-plus";
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import {Plus} from '@element-plus/icons-vue'
import {useTokenStore} from "@/store/token.js";
const tokenStore = useTokenStore();
//控制抽屉是否显示
const visibleDrawer = ref(false)
const visibleDrawer2 = ref(false)
//添加表单数据模型
const articleModel = ref({
  id: '',
  title: '',
  writer:'',
  coverImg: '',
  endTime: '',
  borrowTime: '',
})
// 文章分类数据模型
const categorys = ref([
  // ...（分类数据）
])
import { bookCategoryListService } from '@/api/book.js'
const getBookCategoryList = async () => {
  //获取所有分类
  let resultC = await bookCategoryListService();
  categorys.value = resultC.data
}
getBookCategoryList();

// 用户搜索时选中的分类id



// 用户输入的图书名和作者名
const bookTitle = ref('')
const authorName = ref('')
const categoryId = ref('')

// 文章列表数据模型
const articles = ref([

])

// 分页条数据模型
const pageNum = ref(1)
const total = ref(20)
const pageSize = ref(3)

// 当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
}

// 当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
}
//

//归还图书
const returnBook = async (row) => {
  // 这里可以添加归还图书的逻辑，比如发送请求到后端或者更新状态
  let result = await bookReturnService(row.id)
  ElMessage.success(result.msg?result.msg:"归还成功")
  bookReturnList();
}

// 搜索函数

const bookReturnList=async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    title: bookTitle.value ? bookTitle.value : null,
    writer: authorName.value ? authorName.value : null
  }

  let result = await bookReturnListService(params);
  total.value=result.data.total
  articles.value = result.data.items;

}
bookReturnList();
//图片上传成功回调函数

const uploadSuccess=(result)=>{
  articleModel.value.coverImg= result.data;
  console.log(result.data);
}
//续借逻辑
const borrowAgain = async (row) => {
  // 这里可以添加归还图书的逻辑，比如发送请求到后端或者更新状态
  let result = await borrowAgainService(row.id)
  ElMessage.success(result.msg?result.msg:"续借成功")
  bookReturnList();
}

// 重置搜索条件
const resetSearch = () => {
  bookTitle.value = ''
  authorName.value = ''
  categoryId.value = ''
  // 如果有其他需要重置的内容，也可以在这里添加
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>借阅图书</span>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form inline>
      <!-- ...（分类和状态选择器） -->
      <el-form-item label="图书名：">
        <el-input v-model="bookTitle" placeholder="请输入图书名"></el-input>
      </el-form-item>
      <el-form-item label="作者名：">
        <el-input v-model="authorName" placeholder="请输入作者名"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="bookList()">搜索</el-button>
        <el-button @click="resetSearch()">重置</el-button>
      </el-form-item>
    </el-form> 

    <!-- 文章列表 -->
    <el-table :data="articles" style="width: 100%">
      <el-table-column label="序号" width="100" prop="id" type="index"></el-table-column>
      <el-table-column label="图书名" width="200" prop="title"></el-table-column>
      <el-table-column label="封面" prop="coverImg">
        <template #default="{ row }">
          <img :src="row.coverImg" alt="封面" style="width: 200px; height: auto;">
        </template>
      </el-table-column>
      <el-table-column label="作者" width="200" prop="writer"></el-table-column>
      <el-table-column label="借阅时间" width="200" prop="borrowingTime"></el-table-column>
      <el-table-column label="归还期限" width="200" prop="endTime"></el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button type="success" plain @click="borrowAgain(row)">续借</el-button>
          <el-button type="success" plain @click="returnBook(row)">归还</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->

    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5 ,10, 15]"
                   layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
                   @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    <!-- ...（分页代码） -->
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
/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>