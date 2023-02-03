<template>
    <div class="heading">
        <p>Calories Burned By Activities</p>
    </div>
   <div class="activities">
        <div class="form">
            <div class="mb-3" v-for="(data,index) in request" :key="index">
                <label :for="'ex'+index" class="form-label">{{data.name}}</label>
                <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index" :placeholder="'Enter '+data.name">
            </div>
            <div class="mt-4">
                <button class="btn" @click="getCaloriesBurnedData()">{{this.loder ? "Processing....":"Request Now"}}</button>
            </div>
        </div>
    </div>
    <div class="calory_report">
        <div class="heading">
            <h2>Report - Calories Burned</h2>
        </div>
        <div class="report_calories">
            <ul>
                <li>Activity Name : <span>{{response.name}}</span></li>
                <li>Calories Burned : <span>{{response.total_calories}}</span></li>
            </ul>
        </div>
    </div>
</template>

<script>
import server from "../../../server/backend"
export default {
    name: "CaloriesBurnedByActivity",
    data(){
        return{
            request:[
                {
                    name :"Weight",
                    value:""
                },
                {
                    name :"Activity",
                    value:""
                },
                {
                    name:"Duration",
                    value:""
                }
            ],
            response:{
                total_calories:"",
                name:""
            },
            loder: false
        }
    },
    methods:{
        // =============== Calories Burned By Activities From Api =============
            async getCaloriesBurnedData(){
                this.loder = true
                try{
                    const serverResponse = await server.getCaloriesBurndByActivity(this.request[1].value,this.request[0].value,this.request[2].value);
                    this.response=serverResponse.data[0]
            
            console.log("Calories Calculation By Activity - ",serverResponse.data);
                    this.loder = false;
                }catch(e){
                    console.log("Error - ",e);
                    this.loder = false;
                }
            },
        // ==========X==== Calories Burned By Activities From Api ===X=========

        // =============== Data Clear ================
            clear(){
                this.response = {}
            }
        // ==========X==== Data Clear ====X===========
    }   
}
</script>