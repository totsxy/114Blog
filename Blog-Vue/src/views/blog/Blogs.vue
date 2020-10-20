<template>
  <div>

    <el-backtop></el-backtop>

    <el-timeline class="blog-list">
      <el-timeline-item
        :timestamp="blog.created"
        placement="top"
        v-for="blog in blogs"
      >
        <el-card class="blog-card">

          <el-image :src="blog.cover">
            <div slot="error">
              <el-image src="./static/img/bg.jpg"/>
            </div>
          </el-image>

          <h4>
            <router-link :to="{
              name: 'Detail',params:{blogId:blog.id}
            }">
              {{blog.title}}
            </router-link>
          </h4>

          <p>
            <router-link :to="{
              name: 'Detail',params:{blogId:blog.id}
            }">
              {{blog.summary}}
            </router-link>
          </p>

        </el-card>
      </el-timeline-item>
    </el-timeline>

  </div>
</template>

<script>
  export default {
    name: "Blogs",
    data() {
      return {
        loading: true,
        blogs: new Array(0),
        current: 1,
        pages: 0
      }
    },
    methods: {
      page(current) {
        const app = this

        app.loading = false
        app.$axios.get('/blogs?current=' + current).then(res => {
          app.blogs = app.blogs.concat(res.data.data.records)
          app.current = res.data.data.current
          app.pages = res.data.data.pages
        }).finally(() => {
          app.loading = true
        })
      }
    },
    created() {
      this.page(1)
    },
    mounted() {
      const app = this

      window.onscroll = function () {
        let ch = window.innerHeight
          || Math.min(
            document.documentElement.clientHeight,
            document.body.clientHeight
          )

        let sh = Math.max(
          document.documentElement.scrollHeight,
          document.body.scrollHeight
        )

        let st = Math.max(
          window.pageYOffset,
          document.documentElement.scrollTop,
          document.body.scrollTop
        )

        if (app.loading && (ch + st >= sh) && app.current < app.pages) {
          app.$message.success('加载下一页', {duration: 2000})
          app.page(app.current + 1)
        }
      }
    }, destroyed() {
      window.onscroll = function () {
      }
    }
  }
</script>

<style>
  .blog-list {
    overflow: auto;
    width: 40%;
    margin: 100px auto 0;
    text-align: left;
  }

  .blog-card > * {
    text-align: center;
  }
</style>
