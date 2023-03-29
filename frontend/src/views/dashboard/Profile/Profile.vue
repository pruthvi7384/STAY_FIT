<template>
    <div class="heading">
        <p><span>{{name}}</span> Profile</p>
    </div>
    <div class="activities">
        <div class="form">
            <div class="mb-3" v-for="(data,index) in request" :key="index">
                <label :for="'ex'+index" class="form-label">{{data.name}}</label>
                <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index" :placeholder="'Enter '+data.name">
            </div>
            <div class="mb-3" v-for="(data,index) in otherDetails" :key="index">
                <label :for="'ex'+index" class="form-label">{{data.name}}</label>
                <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index">
            </div>
            <div class="mt-4">
                <button class="btn" @click="updateProfile()">{{this.loder ? "Processing....":"Update Profile"}}</button>
            </div>
        </div>
    </div>
</template>

<script>
import server from "../../../server/backend";
import moment from 'moment';
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
            name:"",
            otherDetails:[
                {
                    name:"Registered Date",
                    value:""
                },
                {
                    name:"Updated Date",
                    value:""
                },
                {
                    name:"Last Login Date",
                    value:""
                }
            ],
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
                    console.log("Profile - ",this.response);
                    this.request = [
                            {
                                name:"First Name",
                                value:this.response.PROFILE.firstName
                            },
                            {
                                name:"Last Name",
                                value:this.response.PROFILE.lastName
                            },
                            {
                                name:"Email Id",
                                value:this.response.PROFILE.emailId
                            }
                        ],
                    this.otherDetails = [
                        {
                            name:"Registered Date",
                            value: this.response.PROFILE.registeredDate == null ? 'NA' : moment(this.response.PROFILE.registeredDate)
                        },
                        {
                            name:"Updated Date",
                            value: this.response.PROFILE.updatedDate == null ? 'NA' : moment(this.response.PROFILE.updatedDate)
                        },
                        {
                            name:"Last Login Date",
                            value: this.response.PROFILE.lastLoginDate == null ? 'NA' : moment(this.response.PROFILE.lastLoginDate)
                        }
                    ],
                    // this.name = this.response.PROFILE.firstName+" "+this.response.PROFILE.lastName
                    this.loder = false;
                }catch(e){
                    console.log("Error - ",e);
                    this.loder = false;
                }
            },
        // ==========X==== Get Profile From Api ===X=========

        // =============== Update Profile ==============
            async updateProfile(){
                this.loder = true;
                if(this.request[0].value == "" || this.request[1].value == "" || this.request[2].value == ""){
                    alert("Please fill in all fields appropriately!");
                    this.loder = false
                }else{
                    try{
                        const requestProfile = {
                            firstName: this.request[0].value,
                            lastName: this.request[1].value,
                            emailId: this.request[2].value
                        }
                        const serverResponse = await server.updateProfile(requestProfile,this.response.PROFILE.id);
                        console.log("Profile Update - ",serverResponse.data);
                        alert(serverResponse.data.MESSAGE);
                        this.loder = false;
                        this.getProfileUser();
                    }catch(e){
                        console.log("Error - ",e);
                        this.loder = false;
                    }
                }
            }
        // ==========X==== Update Profile ====X=========
    },
    mounted(){
        this.getProfileUser();
    }   
}
</script>