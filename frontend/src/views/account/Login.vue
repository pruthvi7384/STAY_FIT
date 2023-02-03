<template>
    <Top/>
    <div class="container-fluid account_form">
        <div class="account_body">
            <div class="heading">
                <h2>Log In Your Self</h2>
            </div>
            <div class="form">
                <div class="mb-3" v-for="(data,index) in login" :key="index">
                    <label :for="'ex'+index" class="form-label">{{data.name}}</label>
                    <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index" :placeholder="'Enter '+data.name">
                </div>
                <div class="mt-4">
                    <button class="btn" @click="logIn()">{{this.loder ? "Processing....":"Login Now"}}</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Top from "../../components/Top"
import server from "../../server/backend"
export default {
    name : 'LogIn',
    data(){
        return{
            login:[
                {
                    value:"",
                    field:"Email Id",
                    name:"Email Id",
                    type:"text"
                },
                {
                    value:"",
                    field:"Password",
                    name:"Password",
                    type:"password"
                }
            ],
            response:{},
            loder : false
        }
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

        // ========= LOGIN API INTEGRATION ==========
            async logIn(){
                this.loder = true;
                const request = {
                    emailId:this.login[0].value,
                    password:this.login[1].value
                }
                if(request.emailId == "" || request.password == ""){
                    alert("Please fill in all fields appropriately!");
                }else{
                    console.log("LOGIN REQUEST - ",request);
                    try{
                        const serverResponse = await server.loginServer(request);
                        this.response = serverResponse.data;
                        //======== LOCAL STORAGE SET ACCESS TOKEN RECIVED FORM SERVER ==========
                        if(this.response.STATUS == "SUCCESS"){
                            localStorage.setItem("ACCESS_TOKEN",this.response.ACCESS_TOKEN);
                            this.login=[
                                {
                                    value:"",
                                    field:"Email Id",
                                    name:"Email Id",
                                    type:"text"
                                },
                                {
                                    value:"",
                                    field:"Password",
                                    name:"Password",
                                    type:"password"
                                }
                            ]
                        }
                        console.log("LOGIN RESPONSE - ",this.response)
                        alert(this.response.MESSAGE);
                        this.loder = false;
                        this.loginCheck()
                    }catch(error){
                        console.log("Error - ",error);
                        this.loder = false
                    }
                }
            }
        // ======X=== LOGIN API INTEGRATION ===X=======
    },
    components:{
        Top
    },
    mounted(){
        this.loginCheck()
    }
}
</script>