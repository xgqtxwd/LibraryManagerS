<script setup>
import { ref } from 'vue'
import { Edit, Delete, Plus } from '@element-plus/icons-vue'
import { bookListService, bookBorrowService, bookUpdateService, bookAddService } from "@/api/book.js";
import { ElMessage } from "element-plus";
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { useTokenStore } from "@/store/token.js";
import useUserInfoStore from '@/store/userinfo.js';

const tokenStore = useTokenStore();
const userStore = useUserInfoStore();

// 控制抽屉是否显示
const visibleDrawer = ref(false)
const visibleDrawer2 = ref(false)

// 添加表单数据模型
const articleModel = ref({
  id: '',
  title: '',
  categoryId: '',
  writer: '',
  coverImg: '',
  content: '',
  quantity: ''
})

// 文章分类数据模型
const categorys = ref([])

import { bookCategoryListService } from '@/api/book.js'
const getBookCategoryList = async () => {
  // 获取所有分类
  let resultC = await bookCategoryListService();
  categorys.value = resultC.data
}
getBookCategoryList();

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
  pageSize.value = size
}

// 当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
}

// 借阅图书
const borrowBook = async (row) => {
  let result = await bookBorrowService(row.id)
  ElMessage.success(result.msg ? result.msg : "借阅成功")
  bookList();
}

// 更新图书信息
const updateBook = async () => {
  let result = await bookUpdateService(articleModel.value);
  ElMessage.success(result.msg ? result.msg : "修改成功")
  visibleDrawer.value = false;
  bookList();
}

// 新增图书
const addBook = async () => {
  let result = await bookAddService(articleModel.value);
  ElMessage.success(result.msg ? result.msg : "添加成功")
  visibleDrawer2.value = false;
  bookList();
}

// 搜索函数
const bookList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    title: bookTitle.value ? bookTitle.value : null,
    writer: authorName.value ? authorName.value : null
  }

  let result = await bookListService(params);
  total.value = result.data.total
  articles.value = result.data.items;
}
bookList();

// 图片上传成功回调函数
const uploadSuccess = (result) => {
  articleModel.value.coverImg = result.data;
  console.log(result.data);
}

// 判断用户是否为管理员
const isAdministrator = () => {
  return userStore.userinfo.privilegeLevel === 1;
}

</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>借阅图书</span>
        <div style="margin-left:400px">
          <el-button v-if="isAdministrator" type="primary" @click="visibleDrawer2 = true">添加图书</el-button>
        </div>
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
        <el-button type="primary" @click="bookList()">搜索</el-button>
        <el-button @click="">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 文章列表 -->
    <el-table :data="articles" style="width: 100%">
      <el-table-column label="序号" width="100" prop="id" type="index"></el-table-column>
      <el-table-column label="图书名" width="200" prop="title"></el-table-column>
      <el-table-column label="作者" width="200" prop="writer"></el-table-column>
      <el-table-column label="分类" prop="categoryId"></el-table-column>
      <el-table-column label="库存" prop="quantity"></el-table-column>
      <el-table-column label="封面" width="400" prop="coverImg">
        <template #default="{ row }">
          <img :src="row.coverImg" alt="封面" style="width: 100px; height: auto;">
        </template>
      </el-table-column>
      <el-table-column label="图书简介" prop="content"></el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="visibleDrawer = true"></el-button>
          <el-button type="success" plain @click="borrowBook(row)">借阅</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                   layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
                   @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
  </el-card>

  <!-- 修改图书抽屉 -->
  <el-drawer v-model="visibleDrawer" title="修改图书" direction="rtl" size="50%">
    <el-form :model="articleModel" label-width="100px">
      <el-form-item label="图书编号">
        <el-input v-model="articleModel.id" placeholder="请输入图书编号"></el-input>
      </el-form-item>
      <el-form-item label="图书名">
        <el-input v-model="articleModel.title" placeholder="请输入书名"></el-input>
      </el-form-item>
      <el-form-item label="作者名">
        <el-input v-model="articleModel.writer" placeholder="请输入书名"></el-input>
      </el-form-item>
      <el-form-item label="图书库存">
        <el-input v-model="articleModel.quantity" placeholder="请输入库存数量"></el-input>
      </el-form-item>
      <el-form-item label="图书分类">
        <el-select placeholder="请选择" v-model="articleModel.categoryId">
          <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="封面">
        <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                   action="/api/upload" name="file" :headers="{'Authorization':tokenStore.token}" :on-success="uploadSuccess">
          <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="文章内容">
        <div class="editor">
          <quill-editor
            theme="snow"
            v-model:content="articleModel.content"
            contentType="html"
          >
          </quill-editor>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateBook()">发布</el-button>
        <el-button type="info" @click="visibleDrawer = false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>

  <!-- 添加图书抽屉 -->
  <el-drawer v-model="visibleDrawer2" title="添加图书" direction="rtl" size="50%">
    <el-form :model="articleModel" label-width="100px">
      <el-form-item label="图书名">
        <el-input v-model="articleModel.title" placeholder="请输入书名"></el-input>
      </el-form-item>
      <el-form-item label="作者名">
        <el-input v-model="articleModel.writer" placeholder="请输入书名"></el-input>
      </el-form-item>
      <el-form-item label="图书库存">
        <el-input v-model="articleModel.quantity" placeholder="请输入库存数量"></el-input>
      </el-form-item>
      <el-form-item label="图书分类">
        <el-select placeholder="请选择" v-model="articleModel.categoryId">
          <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="封面">
        <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                   action="/api/upload" name="file" :headers="{'Authorization':tokenStore.token}" :on-success="uploadSuccess">
          <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="文章内容">
        <div class="editor">
          <quill-editor
            theme="snow"
            v-model:content="articleModel.content"
            contentType="html"
          >
          </quill-editor>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addBook()">添加</el-button>
        <el-button type="info" @click="visibleDrawer2 = false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
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