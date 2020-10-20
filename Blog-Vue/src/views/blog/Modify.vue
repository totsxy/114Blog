<template>
  <div>

    <el-backtop></el-backtop>

    <div class="modify-body">
      <el-form
        label-width="80px"
        :model="modifyForm" :rules="rules" ref="modifyForm"
      >

        <el-form-item label="标题" prop="title">
          <el-input
            v-model="modifyForm.title"
          >
          </el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="summary">
          <el-input
            type="textarea"
            v-model="modifyForm.summary"
          >
          </el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor class="md-editor" v-model="modifyForm.content"/>
        </el-form-item>

        <el-form-item>
          <el-popover
            trigger="hover"
            placement="top-start"
            title="警告"
            content="该功能尚未实现，给您造成的不便敬请谅解。"
          >
            <el-button
              type="primary"
              slot="reference"
            >
              选择封面
            </el-button>
          </el-popover>

          <el-button
            type="primary"
            @click="submitModify()"
          >
            提交编辑
          </el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Modify",
    data() {
      return {
        modifyForm: {
          id: null,
          uid: null,
          title: '',
          summary: '',
          content: ''
        },
        rules: {
          title: [
            {required: true, message: '博客标题不能为空', trigger: 'blur'},
            {min: 3, max: 255, message: '长度在 3 到 255 个字符', trigger: 'blur'}
          ],
          summary: [
            {required: true, message: '博客摘要不能为空', trigger: 'blur'},
            {min: 3, max: 255, message: '长度在 3 到 255 个字符', trigger: 'blur'}
          ],
          content: [
            {required: true, message: '博客内容不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      submitModify() {
        this.$refs.modifyForm.validate((valid) => {
          if (valid) {
            const app = this

            app.$axios.post('/blog/update', app.modifyForm,
              {headers: {"Authorization": app.$store.getters.getAuth}}
            ).then((res) => {
              app.$confirm('操作成功！是否留在当前页面？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText:  '取消',
                type: 'success'
              }).catch(() => {
                app.$router.push("/blogs")
              })
            })
          }
        })
      }
    },
    created() {
      const app = this
      const blogId = app.$route.params.blogId

      if (blogId) {
        app.$axios.get('/blog/' + blogId).then((res) => {
          app.modifyForm = res.data.data
        })
      }
    }
  }
</script>

<style scoped>
  .modify-body {
    width: 60%;
    margin: 100px auto 0;
  }

  .md-editor {
    z-index: 1
  }
</style>
