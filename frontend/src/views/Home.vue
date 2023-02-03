<template>
  <div class="container-fluid home">
    <div class="home_row">
        <div class="heading">
            <div class="welcomeMessage">
              <h2>Welcome <span>To,</span></h2>
            </div>
            <Logo/>
        </div>
        <div class="home_card">
          <div class="card_body">
              <p>If you don't have an account, please register yourself first.</p>
              <router-link to="/registration"><button class="btn">Register Now</button></router-link>
          </div>
          <div class="card_body">
              <p>If you have already an account, please first log in yourself.</p>
              <router-link to="/login"><button class="btn">Login Now</button></router-link>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
import Logo from "../components/Logo"
export default {
    name: "HomeWelcomme",
    components:{
      Logo
    },
    methods:{
      // ========= LOGIN CHECK =============
            loginCheck(){
                const accessToken = localStorage.getItem("ACCESS_TOKEN");
                if(accessToken != null){
                     this.$router.push('/dashboard');
                }
            },
        // =====X=== LOGIN CHECK ===X=========
    },
    mounted(){
      this.loginCheck()
    }
}
</script>
