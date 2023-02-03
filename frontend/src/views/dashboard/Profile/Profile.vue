<template>
    <div class="heading">
        <p><span>{{request[0].value + " " + request[1].value}}</span> Profile</p>
    </div>
    <div class="activities">
        <div class="form">
            <div class="mb-3" v-for="(data,index) in request" :key="index">
                <label :for="'ex'+index" class="form-label">{{data.name}}</label>
                <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index" :placeholder="'Enter '+data.name">
            </div>
             <div class="mb-3" v-for="(key,value) in otherDetails" :key="key">
                <label :for="'ex'+index" class="form-label">{{value}}</label>
                <input type="text" class="form-control" :id="'ex'+index" disabled>
            </div>
            <div class="mt-4">
                <button class="btn" @click="getBMI()">{{this.loder ? "Processing....":"Update Profile"}}</button>
            </div>
        </div>
    </div>
</template>

<script>
import server from "../../../server/backend"
export default {
    name: "ProFile",
    data(){
        return{
            request:[
                {
                    name:"First Name",
                    value:""
                },
                {
                    name:"Last Name",
                    value:""
                },
                {
                    name:"Email Id",
                    value:""
                }
            ],
            otherDetails:{
                "Registered Date":"",
                "Updated Date": "",
                "Last Login Date":""
            },
            response:{},
            loder: false
        }
    },
    methods:{
        // =============== Get Profile From Api =============
            async getProfileUser(){
                this.loder = true
                try{
                    const serverResponse = await server.getProfile();
                    this.response=serverResponse.data
                    console.log("Profile - ",serverResponse.data);
                    this.loder = false;
                }catch(e){
                    console.log("Error - ",e);
                    this.loder = false;
                }
            },
        // ==========X==== Get Profile From Api ===X=========

        // =============== Data Clear ================
            clear(){
                this.response = {}
            }
        // ==========X==== Data Clear ====X===========
    },
    mounted(){
        this.getProfileUser();
    }   
}
</script>