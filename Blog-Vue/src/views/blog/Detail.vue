<template>
  <div>

    <el-backtop></el-backtop>

    <div class="blog-body">
      <el-card>

        <h2>{{ blog.title }}</h2>

        <el-link
          :icon="starMode"
          @click="unstar"
        >
          <span>{{starNum}}</span>
        </el-link>

        <br>
        <br>

        <el-link
          icon="el-icon-edit"
          v-if="ownBlog"
        >
          <router-link :to="{name: 'Modify', params: {blogId: blog.id}}">
            编辑
          </router-link>
        </el-link>

        <el-popconfirm
          title="确定删除该评论？"
          @onConfirm="delBlog"
        >
          <span slot="reference">
            <el-link
              icon="el-icon-delete"
              v-if="ownBlog">
            删除
          </el-link>
          </span>
        </el-popconfirm>

        <el-divider></el-divider>
        <div class="markdown-body" v-html="blog.content"></div>

        <br><br><br>
        <p class="timestamp">更新于{{blog.created}}</p>
        <el-divider></el-divider>

        <el-form
          label-width="0px"
          :model="commentForm" :rules="rules" ref="commentForm"
        >
          <el-form-item label="" prop="content">
            <el-input
              v-model="commentForm.content"
              placeholder="请输入评论"
            >
              <el-button @click="submitComment()" slot="append">
                评论
              </el-button>
            </el-input>
          </el-form-item>
        </el-form>

      </el-card>

      <p>最新评论</p>

      <el-card>

        <ul class="comment-ul">
          <li class="comment-body" v-for="value in comments.values">

            <el-avatar src="">
              <img src="../../../static/img/bg.jpg" alt=""/>
            </el-avatar>

            &nbsp&nbsp

            <p class="comment">
              <span class="comment-owner">{{value.owner}}: </span>
              {{value.content}}
              <br>
              发表于：{{value.created}}
            </p>

            <p class="delete-button">
              <el-popconfirm
                title="确定删除该评论？"
                @onConfirm="deleteComm(value.id)"
              >
                <el-link
                  slot="reference"
                  icon="el-icon-delete"
                  v-if="isOwnerComm(value.uid)">
                  删除
                </el-link>
              </el-popconfirm>
            </p>

            <el-divider class="comment-line"></el-divider>

          </li>
        </ul>

        <el-pagination style="margin-top: 20px" background
                       layout="prev, pager, next"
                       :total="comments.total"
                       :current-page="comments.current"
                       :page-size="comments.pages"
                       @current-change="flushComment"
        >
        </el-pagination>

      </el-card>
    </div>

  </div>
</template>

<script>
  import 'github-markdown-css/github-markdown.css'

  export default {
    name: "Detail",
    data() {
      return {
        blog: {
          uid: null,
          title: '',
          summary: '',
          content: ''
        },
        comments: {
          values: new Array(0),
          current: 1,
          pages: 0,
          total: 0
        },
        commentForm: {
          bid: 0,
          content: ''
        },
        rules: {
          content: [
            {required: true, message: '评论内容不能为空', trigger: 'blur'},
            {min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: 'blur'}
          ]
        },
        ownBlog: false,
        starNum: 0,
        starMode: 'el-icon-star-off'
      }
    },
    methods: {
      flushStar() {
        const app = this
        const blogId = app.$route.params.blogId

        app.$axios.get('/blog/' + blogId + '/stars',
          {headers: {"Authorization": app.$store.getters.getAuth}}
        ).then((res) => {
          app.starNum = res.data.data.num
          app.starMode = res.data.data.liked
            ? 'el-icon-star-on'
            : 'el-icon-star-off'
        })
      },
      flushComment(current) {
        const app = this
        const blogId = app.$route.params.blogId

        app.$axios.get(
          '/blog/' + blogId + '/comments?current=' + current
        ).then((res) => {
          app.comments.values = res.data.data.records
          app.comments.current = res.data.data.current
          app.comments.pages = res.data.data.size
          app.comments.total = res.data.data.total
        })
      },
      unstar() {
        const app = this
        const blogId = app.$route.params.blogId

        app.$axios.get('/star/update/' + blogId,
          {headers: {"Authorization": app.$store.getters.getAuth}}
        ).then((res) => {
          app.flushStar()
        })
      },
      isOwnerComm(id) {
        return id === this.$store.getters.getUser.id
      },
      deleteComm(id) {
        const app = this
        app.$axios.get('/comment/delete/' + id,
          {headers: {"Authorization": app.$store.getters.getAuth}}
        ).then((res) => {
          app.$message.success('删除成功', {duration: 2000})
          app.flushComment(app.comments.current)
        })
      },
      delBlog() {
        const app = this
        const blogId = app.$route.params.blogId

        app.$axios.get('/blog/delete/' + blogId,
          {headers: {"Authorization": app.$store.getters.getAuth}}
        ).then((res) => {
          app.$message.success('删除成功', {duration: 2000})
          setTimeout(() => {
            app.$router.push('/blogs')
          }, 1000)
        })
      },
      submitComment() {
        const app = this
        app.$refs.commentForm.validate((valid) => {
          if (valid) {
            app.$axios.post('/comment/create', app.commentForm,
              {headers: {"Authorization": app.$store.getters.getAuth}}
            ).then((res) => {
              app.$message.success('评论成功', {duration: 2000})
              app.$refs.commentForm.reset()
              app.flushComment(app.comments.current)
            })
          }
        })
      },
    },
    created() {
      const app = this
      const blogId = app.$route.params.blogId

      app.$axios.get('/blog/' + blogId).then((res) => {
        app.blog = res.data.data

        const MarkdownIt = require('markdown-it'),
          md = new MarkdownIt()

        app.blog.content = md.render(app.blog.content)
        app.ownBlog = (
          app.$store.getters.getUser
          && app.blog.uid === app.$store.getters.getUser.id
        )
      })

      app.flushStar()
      app.flushComment(1)
      app.commentForm.bid = blogId
    }, mounted() {
      document.body.scrollTop = 0             // chrome
      document.documentElement.scrollTop = 0  // firefox
      window.pageYOffset = 0                  // safari
    }
  }
</script>

<style scoped>

  ul, li {
    margin: 0;
    padding: 0;
    list-style: none;
  }

  .blog-body {
    width: 50%;
    margin: 100px auto 0;
  }

  .markdown-body {
    text-align: left;
  }

  .timestamp {
    float: right;
    margin: 0;
    padding: 0;
    font-size: 12px;
  }

  .comment-ul {
    text-align: left;
  }

  .comment-body {
    height: 70px;
    font-size: 14px;
    line-height: 35px;
    vertical-align: middle;
  }

  .comment {
    display: inline-block;
    margin: 0;
    padding: 0;
    font-size: 13px;
  }

  .comment-owner {
    color: #f55448;
    font-size: 16px;
  }

  .delete-button {
    display: inline-block;
    float: right;
  }

  .comment-line {
    margin: 0 10px;
    padding: 0;
  }
</style>
