<template>
  <div class="root-panel">
    <el-container>

      <el-form
        class="login-form" label-width="0"
        :model="loginForm" :rules="rules" ref="loginForm"
      >

        <h2>Blog Login</h2>

        <el-form-item prop="name">
          <el-input
            type="text"
            v-model="loginForm.name"
            placeholder="请输入账户"

          >
            <i slot="prefix" class="el-input__icon el-icon-user"></i>
          </el-input>
        </el-form-item>

        <el-form-item prop="pass">
          <el-input
            type="password"
            v-model="loginForm.pass"
            placeholder="请输入密码"
          >
            <i slot="prefix" class="el-input__icon el-icon-lock"></i>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button
            class="form-submit"
            type="primary" @click="login()"
            :loading="loginBtStatus.isLoading"
            :disabled="loginBtStatus.isDisable"
          >
            {{loginBtStatus.text}}
          </el-button>
        </el-form-item>

        <el-link
          class="register-link" type="primary"
          @click="isVisible = true"
        >
          还没有账户？去注册
        </el-link>

        <el-dialog
          title="注册"
          width="25%"
          :visible.sync="isVisible"
        >

          <el-form
            label-width="0"
            :model="registerForm" :rules="rules" ref="registerForm"
          >
            <el-form-item prop="name">
              <el-input
                type="text"
                v-model="registerForm.name"
                placeholder="请输入账户"

              >
                <i slot="prefix" class="el-input__icon el-icon-user"></i>
              </el-input>
            </el-form-item>

            <el-form-item prop="pass">
              <el-input
                type="password"
                v-model="registerForm.pass"
                placeholder="请输入密码"
              >
                <i slot="prefix" class="el-input__icon el-icon-lock"></i>
              </el-input>
            </el-form-item>

            <el-form-item prop="mail">
              <el-input
                type="password"
                v-model="registerForm.mail"
                placeholder="请输入邮箱"
              >
                <i slot="prefix" class="el-input__icon el-icon-lock"></i>
              </el-input>
            </el-form-item>

          </el-form>

          <span slot="footer" class="dialog-footer">
            <el-button @click="isVisible = false">取 消</el-button>
             <el-button
               type="primary" @click="register()"
               :loading="registerBtStatus.isLoading"
               :disabled="registerBtStatus.isDisable"
             >
            {{registerBtStatus.text}}
          </el-button>
          </span>

        </el-dialog>

      </el-form>

    </el-container>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        loginForm: {
          name: '',
          pass: '',
        },
        registerForm: {
          name: '',
          pass: '',
          mail: ''
        },
        loginBtStatus: {
          isLoading: false,
          isDisable: false,
          text: '登录',
        },
        registerBtStatus: {
          isLoading: false,
          isDisable: false,
          text: '注册',
        },
        isVisible: false,
        rules: {
          name: [
            {required: true, message: '账户不能为空', trigger: 'blur'},
            {min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur'}
          ],
          pass: [
            {required: true, message: '密码不能为空', trigger: 'blur'},
            {min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur'}
          ],
          mail: [
            {required: true, message: '邮箱不能为空', trigger: 'blur'},
          ]
        }
      }
    },
    methods: {
      login() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            const app = this

            app.commitLogin()
            app.$axios.post('/login', app.loginForm).then(res => {
              // 数据共享
              app.$store.commit('setAuth', res.headers['authorization'])
              app.$store.commit('setUser', res.data.data)
              // 页面跳转
              app.$router.push('/blogs')
            }).finally(() => {
              app.cancelLogin()
            })
          }
        })
      },
      register() {
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            const app = this

            app.commitRegister()
            app.$axios.post('/register', app.registerForm).then(res => {
              app.$message.success('注册成功', {duration: 2000})
              app.loginForm.name = app.registerForm.name
              app.loginForm.pass = app.registerForm.pass
              app.isVisible = false
            }).finally(() => {
              app.cancelRegister()
            })
          }
        })
      },
      // 提交请求
      commitLogin() {
        this.loginBtStatus.isLoading = true
        this.loginBtStatus.isDisable = true
        this.loginBtStatus.text = '登录中'
      },
      commitRegister() {
        this.registerBtStatus.isLoading = true
        this.registerBtStatus.isDisable = true
        this.registerBtStatus.text = '提交中'
      },
      // 取消请求
      cancelLogin() {
        this.loginBtStatus.isLoading = false
        this.loginBtStatus.isDisable = false
        this.loginBtStatus.text = '登录'
      },
      cancelRegister() {
        this.registerBtStatus.isLoading = false
        this.registerBtStatus.isDisable = false
        this.registerBtStatus.text = '注册'
      }
    }
  }
</script>

<style>
  .root-panel {
    width: 100%;
    height: 100%;
    background: url("../../../static/img/bg.jpg") no-repeat;
    background-size: cover;
  }

  .login-form {
    width: 18%;
    margin: 120px auto 0;
    padding: 40px;
    background-color: rgba(255, 255, 255, 0.7);
    box-shadow: 5px 5px 5px rgba(2, 27, 13, 0.5);
    border-radius: 20px;
  }

  .el-form-item__content {
    margin: 0;
    padding: 0;
  }

  .el-input__inner {
    background-color: rgba(255, 255, 255, 0.4);
  }

  .form-submit {
    width: 100%;
    border-radius: 50px;
  }

  .register-link {
    float: right;
    font-size: 12px;
  }
</style>
