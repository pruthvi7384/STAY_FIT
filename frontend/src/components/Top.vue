<template>
  <div class="container-fluid fixed-top">
    <div class="d-flex justify-content-between align-items-center top_bar">
        <Logo/>
        <div>
            <router-link to="/"><button class="btn"><span><i class="fa-solid fa-circle-left"></i></span> BACK</button></router-link>
        </div>
    </div>
  </div>
</template>

<script>
import Logo from "../components/Logo"
export default {
    name:"TopBar",
    components:{
      Logo
    }
}
</script>
