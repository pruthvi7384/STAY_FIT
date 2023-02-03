<template>
    <div class="heading">
        <p>Calculate Body Fat Percentage</p>
    </div>
    <div class="panal">
        <div class="request_form">
            <div class="heading">
                <h2>Request - Calculate Body Fat</h2>
            </div>
            <div class="form">
                <div class="mb-3">
                    <label class="form-label">Gender</label>
                    <select v-model="gender" class="form-select" aria-label="Default select example">
                        <option value="">Select your gender</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                    </select>
                </div>
                <div class="mb-3" v-for="(data,index) in request" :key="index">
                    <label :for="'ex'+index" class="form-label">{{data.name}}</label>
                    <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index" :placeholder="'Enter '+data.name">
                </div>
                <div class="mt-4">
                    <button class="btn m-1" @click="getFat()">{{this.loder ? "Processing....":"Send Request"}}</button>
                    <button class="btn m-1" style="background-color: red" @click="clear()">Clear Now</button>
                </div>
            </div>
        </div>
        <div class="response">
            <div class="heading">
                <h2>Report - Body Fat</h2>
            </div>
            <ul class="response_row">
                <li v-for="(key,value) in response" :key="key">
                    {{value}} : <span>{{key}}</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import server from "../../../server/backend"
export default {
    name: "BodyFatCalculater",
    data(){
        return{
            request:[
                {
                    name:"Age",
                    value:""
                },
                {
                    name:"Weight",
                    value:""
                },
                {
                    name:"Height",
                    value:""
                },
                {
                    name:"Neck",
                    value:""
                },
                {
                    name:"Waist",
                    value:""
                },
                {
                    name:"Hip",
                    value:""
                }
            ],
            gender : "",
            response:{
                    "Body Fat (U.S. Navy Method)":"",
                    "Body Fat Category":"",
                    "Body Fat Mass":"",
                    "Lean Body Mass":"",
                    "Body Fat (BMI method)":""
                },
            loder: false
        }
    },
    methods:{
        // =============== Calculate Body Fat From Api =============
            async getFat(){
                this.loder = true
                try{
                    const serverResponse = await server.getFatPersentage(this.request,this.gender);
                    this.response=serverResponse.data.data
                    console.log("Body Fat Calculation - ",serverResponse.data.data);
                    this.loder = false;
                }catch(e){
                    console.log("Error - ",e);
                    this.loder = false;
                }
            },
        // ==========X==== Calculate Body Fat From Api ===X=========

        // =============== Data Clear ================
            clear(){
                this.request=[
                    {
                        name:"Age",
                        value:""
                    },
                    {
                        name:"Gender"
                    },
                    {
                        name:"Weight",
                        value:""
                    },
                    {
                        name:"Height",
                        value:""
                    },
                    {
                        name:"Neck",
                        value:""
                    },
                    {
                        name:"Waist",
                        value:""
                    },
                    {
                        name:"Hip",
                        value:""
                    }
                ],
                this.response={
                    "Body Fat (U.S. Navy Method)":"",
                    "Body Fat Category":"",
                    "Body Fat Mass":"",
                    "Lean Body Mass":"",
                    "Body Fat (BMI method)":""
                }
            }
        // ==========X==== Data Clear ====X===========
    }   
}
</script>