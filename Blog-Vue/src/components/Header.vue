<template>
  <div id="nav">
    <el-row class="header-body">

      <el-col class="header-col" :span="12">
        <div class="grid-content">

          <a href="/"><span class="el-icon-position"></span></a>
          &nbsp;<a href="/">114 Blog</a>

        </div>
      </el-col>

      <el-col class="header-col" :span="12">
        <div class="grid-content">

          <a href="http://verineliu.com:8080/myTang/">Links</a>
          <a href="http://www.github.com/TSOTHsxy">About</a>

          <el-popover
            placement="bottom"
            width="200"
            trigger="click"
          >

            <el-collapse accordion>
              <el-collapse-item title="用户信息">
                <div>模块尚未实现</div>
              </el-collapse-item>

              <el-collapse-item title="发表设置">
                <div>模块尚未实现</div>
              </el-collapse-item>

              <el-collapse-item title="系统设置">
                <div>模块尚未实现</div>
              </el-collapse-item>
            </el-collapse>

            <a slot="reference">Self</a>

            <div class="options">

              <el-tooltip
                effect="dark"
                content="发表博客"
                placement="left"
              >
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  @click="$router.push('/blog/add')"
                  circle
                ></el-button>
              </el-tooltip>

              <el-tooltip
                effect="dark"
                content="修改信息"
                placement="bottom"
              >
                <el-button
                  type="info"
                  icon="el-icon-edit"
                  circle></el-button>
              </el-tooltip>

              <el-tooltip
                effect="dark"
                :content="btStatus.title"
                placement="right"
              >
                <el-button
                  type="danger"
                  :icon="btStatus.style"
                  circle
                  @click="commit()"
                ></el-button>

              </el-tooltip>

            </div>

          </el-popover>

          <el-popover
            trigger="hover"
            placement="top-start"
            title="警告"
            content="该功能尚未实现，给您造成的不便敬请谅解。"
          >
            <a slot="reference">
              <span class="el-icon-search"></span>
            </a>
          </el-popover>

        </div>
      </el-col>

    </el-row>
    <el-divider class="idivider"></el-divider>
  </div>
</template>

<script>
  export default {
    name: "Header",
    data() {
      return {
        btStatus: {
          style: 'el-icon-download',
          title: '登录'
        }
      }
    },
    methods: {
      commit() {
        const app = this
        const auth = app.$store.getters.getAuth

        if (auth) {
          app.$axios.get('/logout',
            {headers: {"Authorization": auth}}
          ).then((res) => {
            app.$message.success('登出成功', {duration: 2000})
            app.$store.commit('xxxx')

            this.btStatus.title = '登录'
            this.btStatus.style = 'el-icon-download'
          })
        } else {
          app.$router.push('/login')
        }
      }
    },
    created() {
      if (this.$store.getters.getAuth) {
        this.btStatus.title = '登出'
        this.btStatus.style = 'el-icon-upload2'
      } else {
        this.btStatus.title = '登录'
        this.btStatus.style = 'el-icon-download'
      }
    }
  }
</script>

<style>
  a, a:link, a:visited, a:hover, a:active, p {
    cursor: pointer;
    color: gray;
    text-decoration: none;
  }

  #nav {
    position: fixed;
    width: 100%;
    left: 0;
    top: 0;
    z-index: 1000;
  }

  .header-body {
    height: 70px;
    background-color: rgb(255, 255, 255);
  }

  .header-col {
    display: flex;
    height: 100%;
    color: gray;
    text-align: center;
    font: 18px bold;
  }

  .grid-content {
    align-self: center;
    margin: 0 auto;
  }

  .grid-content > * {
    margin: 10px;
  }

  .options {
    padding: 10px;
    text-align: center;
  }

  .idivider {
    margin: 0 !important;
    padding: 0;
  }
</style>
