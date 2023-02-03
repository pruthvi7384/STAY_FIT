<template>
  <div class="container-fluid fixed-top" id="after_login">
    <div class="d-flex justify-content-between align-items-center top_bar">
        <Logo/>
        <div>
            <button class="btn" style="background-color:red" @click="logout()"><span><i class="fa-solid fa-circle-left"></i></span> LOGOUT</button>
        </div>
    </div>
  </div>
</template>

<script>
import Logo from "../components/Logo"
export default {
    name:"AfterLoginTop",
    components:{
        Logo
    },
    methods:{
        logout(){
            localStorage.clear()
            this.$router.push('/')
        }
    },
    computed(){
        this.logout()
    }
}
</script>