<template>

</template>
<div v-for="rank in ranks">
  <p>{{rank.title}}</p>
  <p>{{rank.hot}}</p>
</div>
<script>
export default {
  name: "Rank",
  data(){
    return{
      ranks:null
    }

  }
}
</script>

<style scoped>

</style>