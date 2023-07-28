<template>
  <div>
    <div class="menu">
      <a> 
        파일 확장자 차단
      </a>
    </div>



    <div>
      <h3>파일 업로드</h3>
    </div>

    <div> 
      <h3>고정 확장자</h3>
      <form></form>
      
    </div>

    <div>
      <h3>커스텀 확장자</h3>
    </div>
    
    
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.menu {
  background: darkslateblue;
  padding: 25px;
  border-radius: 5px;
}

.menu a {
  color: white;
  padding: 15px;
}
</style>
