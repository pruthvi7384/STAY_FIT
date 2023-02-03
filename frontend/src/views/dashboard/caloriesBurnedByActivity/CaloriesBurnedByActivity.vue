<template>
    <div class="heading">
        <p>Calories Burned By Activities</p>
    </div>
    <div class="activities">
      
    </div>
</template>

<script>
import server from "../../../server/backend"
export default {
    name: "CaloriesBurnedByActivity",
    data(){
        return{
            request:{
                activity: "Football",
                weight:"107",
                duration:"30"
            },
            response:{},
            loder: false
        }
    },
    methods:{
        // =============== Calories Burned By Activities From Api =============
            async getCaloriesBurnedData(){
                this.loder = true
                try{
                    const serverResponse = await server.getCaloriesBurndByActivity(this.request.activity,this.request.weight,this.request.duration);
                    this.response=serverResponse.data
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