<template>
  <div id="app">
    <transition name="el-fade-in" mode="out-in">
      <router-view />
    </transition>
  </div>
</template>

<script>
export default {
  name: "app"
};
</script>
<style lang="less">
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  min-height: 100vh;
  
	background: linear-gradient(
	calc(340deg), 
	rgba(163, 163, 163, 0.09) 0%, 
	rgba(163, 163, 163, 0.09) 33.3%, 
	rgba(100, 100, 100, 0.09) 33.3%, 
	rgba(100, 100, 100, 0.09) 66.6%, 
	rgba(162, 162, 162, 0.09) 66.6%, 
	rgba(162, 162, 162, 0.09) 99%), 
	linear-gradient(calc(314deg), 
	rgba(193, 193, 193, 0.06) 0%, 
	rgba(193, 193, 193, 0.06) 33.3%, 
	rgba(169, 169, 169, 0.06) 33.3%, 
	rgba(169, 169, 169, 0.06) 66.6%, 
	rgba(92, 92, 92, 0.06) 66.6%, 
	rgba(92, 92, 92, 0.06) 99%), 
	linear-gradient(calc(373deg), 
	rgba(45, 45, 45, 0.03) 0%, 
	rgba(45, 45, 45, 0.03) 33.3%, 
	rgba(223, 223, 223, 0.03) 33.3%, 
	rgba(223, 223, 223, 0.03) 66.6%, 
	rgba(173, 173, 173, 0.03) 66.6%, 
	rgba(173, 173, 173, 0.03) 99%), 
	linear-gradient(calc(307deg), 
	rgba(226, 226, 226, 0.06) 0%, 
	rgba(226, 226, 226, 0.06) 33.3%, 
	rgba(81, 81, 81, 0.06) 33.3%, 
	rgba(81, 81, 81, 0.06) 66.6%, 
	rgba(186, 186, 186, 0.06) 66.6%, 
	rgba(186, 186, 186, 0.06) 99%), 
	linear-gradient(calc(376deg), 
	rgba(225, 225, 225, 0.04) 0%, 
	rgba(225, 225, 225, 0.04) 33.3%, 
	rgba(95, 95, 95, 0.04) 33.3%, 
	rgba(95, 95, 95, 0.04) 66.6%, 
	rgba(39, 39, 39, 0.04) 66.6%, 
	rgba(39, 39, 39, 0.04) 99%), 
	linear-gradient(calc(481deg), 
	rgba(184, 184, 184, 0.06) 0%, 
	rgba(184, 184, 184, 0.06) 33.3%, 
	rgba(0, 0, 0, 0.06) 33.3%, 
	rgba(0, 0, 0, 0.06) 66.6%, 
	rgba(140, 140, 140, 0.06) 66.6%, 
	rgba(140, 140, 140, 0.06) 99.9%), 
	linear-gradient(calc(570deg), 
	rgba(40, 40, 40, 0.07) 0%, 
	rgba(40, 40, 40, 0.07) 33.3%, 
	rgba(214, 214, 214, 0.07) 33.3%, 
	rgba(214, 214, 214, 0.07) 66.6%, 
	rgba(190, 190, 190, 0.07) 66.6%, 
	rgba(190, 190, 190, 0.07) 99.9%), 
	linear-gradient(calc(328deg), 
	rgba(230, 230, 230, 0) 0%, 
	rgba(230, 230, 230, 0) 33.3%, 
	rgba(241, 241, 241, 0) 33.3%, 
	rgba(241, 241, 241, 0) 66.6%, 
	rgba(55, 55, 55, 0) 66.6%, 
	rgba(55, 55, 55, 0) 99%), 
	linear-gradient(calc(43deg), 
	rgb(38, 227, 186), 
	rgb(11, 239, 15));
}
</style>
