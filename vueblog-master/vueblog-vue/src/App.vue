<template>
  <div id="app">
    <router-view name='nav'></router-view>
    <router-view></router-view>
    <router-view  name="blogDetail"></router-view>
    <router-view  name="comment"></router-view>
  </div>
</template>

<style>
  #app {
    max-width: 960px;
    margin: 0 auto;
  }

</style>
