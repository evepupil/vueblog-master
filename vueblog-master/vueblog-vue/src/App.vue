<template>
  <div id="app">
    <router-view name='nav'></router-view>
    <router-view></router-view>
    <router-view  name="blogDetail"></router-view>
    <router-view  name="comment"></router-view>
    <router-view  name="Search"></router-view>
    <router-view name="MyPost"></router-view>
    <router-view name="UserCenter"></router-view>
  </div>
</template>

<style>
  #app {
    max-width: 960px;
    margin: 0 auto;
  }

</style>
