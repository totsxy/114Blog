<template>
  <div id="app">

    <Header v-if="$route.meta.hasHeader"></Header>

    <keep-alive>
      <transition :name="transitionType">　
        <router-view
          v-if="$route.meta.keepAlive"
          class="child-view"></router-view>
      </transition>
    </keep-alive>

    <transition :name="transitionType">　
      <router-view
        v-if="!$route.meta.keepAlive"
        class="child-view"></router-view>
    </transition>

  </div>
</template>

<script>
  import Header from "./components/Header"

  export default {
    name: 'App',
    components: {Header},
    data() {
      return {
        transitionType: 'slide-left'
      }
    },
    watch: {
      '$route'(to, from) {
        const td = to.path.split('/').length
        const fd = from.path.split('/').length
        this.transitionType = td < fd ? 'slide-right' : 'slide-left'
      }
    }
  }
</script>

<style>
  #app {
    width: 100%;
    height: 100%;
    color: gray;
    text-align: center;
  }

  .child-view {
    width: 100%;
    height: 100%;
    transition: all 0.7s cubic-bezier(.55, 0, .1, 1);
  }

  .slide-left-enter, .slide-right-leave-active {
    opacity: 0;
    -webkit-transform: translate(60px, 0);
    transform: translate(60px, 0);
  }

  .slide-left-leave-active, .slide-right-enter {
    opacity: 0;
    -webkit-transform: translate(-60px, 0);
    transform: translate(-60px, 0);
  }
</style>
