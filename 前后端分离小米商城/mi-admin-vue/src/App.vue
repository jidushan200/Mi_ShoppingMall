<script setup lang="ts">

</script>

<template>
  <n-message-provider>
    <n-dialog-provider>
      <router-view/>
    </n-dialog-provider>
  </n-message-provider>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
