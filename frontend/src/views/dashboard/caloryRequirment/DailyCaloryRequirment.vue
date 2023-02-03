<template>
   <div class="heading">
        <p>Cheack Daily Calory Requirments</p>
    </div>
    <div class="panal">
        <div class="request_form">
            <div class="heading">
                <h2>Request - Daily Calory Requirment</h2>
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
                    <label :for="'ex'+index"  class="form-label">{{data.name}}</label>
                    <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index" :placeholder="'Enter '+data.name">
                </div>
                <div class="mb-3">
                    <label class="form-label">Level</label>
                    <select v-model="level" class="form-select" aria-label="Default select example">
                        <option value="">Select your exciease level</option>
                        <option value="level_1">Sedentary: little or no exercise</option>
                        <option value="level_2">Exercise 1-3 times/week</option>
                        <option value="level_3">Exercise 4-5 times/week</option>
                        <option value="level_4">Daily exercise or intense exercise 3-4 times/week</option>
                        <option value="level_5">Intense exercise 6-7 times/week</option>
                        <option value="level_6">Very intense exercise daily, or physical job</option>
                    </select>
                </div>
                <div class="mt-4">
                    <button class="btn m-1" @click="getDailyCalory()">{{this.loder ? "Processing....":"Send Request"}}</button>
                    <button class="btn m-1" style="background-color: red" @click="clear()">Clear Now</button>
                </div>
            </div>
        </div>
        <div class="response">
            <div class="heading">
                <h2>Report - Daily Calory</h2>
            </div>
            <ul class="response_row">
                <li>BMR : <span>{{response.BMR == "" ? 'NA' : response.BMR}}</span></li>
                 <li v-for="(key,value,index) in response.goals" :key="index">
                    {{value}} : <span>{{key}}</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import server from '../../../server/backend'
export default {
    name:"DailyCaloryRequirment",
    data(){
        return{
            request:[
                {
                    name:"Age",
                    value:""
                },
                {
                    name:"Height",
                    value:""
                },
                {
                    name:"Weight",
                    value:""
                }
            ],
            gender : "",
            level : "",
            response:{
                "BMR": "",
                "goals": {
                    "maintain weight": "",
                    "Mild weight loss": {
                        "loss weight": "",
                        "calory": ""
                    },
                    "Weight loss": {
                        "loss weight": "",
                        "calory": ""
                    },
                    "Extreme weight loss": {
                        "loss weight": "",
                        "calory": ""
                    },
                    "Mild weight gain": {
                        "gain weight": "",
                        "calory": ""
                    },
                    "Weight gain": {
                        "gain weight": "",
                        "calory": ""
                    },
                    "Extreme weight gain": {
                        "gain weight": "",
                        "calory": ""
                    }
                }
            },
            loder: false
        }
    },
    methods:{
        // ============= Daily Calory Requirmrnts Calculate From API ============
            async getDailyCalory(){
                this.loder = true
                try{
                    const serverResponse = await server.getCaloryRequirments(this.request,this.level,this.gender);
                    this.response=serverResponse.data.data
                    console.log("Daily Calory Requirment - ",this.response);
                    this.loder = false;
                }catch(e){
                    console.log("Error - ",e);
                    this.loder = false;
                }
            },
        // ========X==== Daily Calory Requirmrnts Calculate From API ===X========

        // ============= Clear Data ==============
            clear(){
                this.request=[
                    {
                        name:"Age",
                        value:""
                    },
                    {
                        name:"Gender",
                        value:""
                    },
                    {
                        name:"Height",
                        value:""
                    },
                    {
                        name:"Weight",
                        value:""
                    }
                ],
                this.response={
                    "BMR": "",
                    "goals": {
                        "maintain weight": "",
                        "Mild weight loss": {
                            "loss weight": "",
                            "calory": ""
                        },
                        "Weight loss": {
                            "loss weight": "",
                            "calory": ""
                        },
                        "Extreme weight loss": {
                            "loss weight": "",
                            "calory": ""
                        },
                        "Mild weight gain": {
                            "gain weight": "",
                            "calory": ""
                        },
                        "Weight gain": {
                            "gain weight": "",
                            "calory": ""
                        },
                        "Extreme weight gain": {
                            "gain weight": "",
                            "calory": ""
                        }
                    }
                }
            }
        // ========X==== Clear Data ====X=========
    }
}
</script>