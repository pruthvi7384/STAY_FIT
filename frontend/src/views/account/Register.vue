<template>
    <Top/>
    <div class="container-fluid account_form">
        <div class="account_body">
            <div class="heading">
                <h2>Register Your Self</h2>
            </div>
            <div class="form">
                <div class="mb-3" v-for="(data,index) in login" :key="index">
                    <label :for="'ex'+index" class="form-label">{{data.name}}</label>
                    <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index" :placeholder="'Enter '+data.name">
                </div>
                <div class="mt-4">
                    <button class="btn" @click="register()">{{this.loder ? "Processing....":"Register Now"}}</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Top from "../../components/Top"
import server from "../../server/backend"
export default {
    name : 'RegisterAction',
    data(){
        return{
            login:[
                {
                    value:"",
                    field:"First Name",
                    name:"First Name",
                    type:"text"
                },
                {
                    value:"",
                    field:"Last Name",
                    name:"Last Name",
                    type:"text"
                },
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

        // ========= REGISTRATION API INTEGRATION ==========
            async register(){
                this.loder = true;
                const request = {
                    firstName:this.login[0].value,
                    lastName:this.login[1].value,
                    emailId:this.login[2].value,
                    password:this.login[3].value
                }
                if(request.firstName =="" || request.lastName == "" || request.emailId == "" || request.password == ""){
                    alert("Please fill in all fields appropriately!");
                }else{
                    console.log("REGISTRATION REQUEST - ",request);
                    try{
                        const serverResponse = await server.registerServer(request);
                        this.response = serverResponse.data;
                        console.log("REGISTRATION RESPONSE - ",this.response)
                        alert(this.response.MESSAGE);
                        this.loder = false;
                        if(this.response.STATUS != "FAILURE"){
                            this.$router.push('/login')
                            this.login = [
                                {
                                    value:"",
                                    field:"First Name",
                                    name:"First Name",
                                    type:"text"
                                },
                                {
                                    value:"",
                                    field:"Last Name",
                                    name:"Last Name",
                                    type:"text"
                                },
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
                    }catch(error){
                        console.log("Error - ",error)
                        this.loder = false
                    }
                }
            }
        // ======X=== REGISTRATION API INTEGRATION ===X=======
    },
    components:{
        Top
    },
    mounted(){
        this.loginCheck()
    }
}
</script>