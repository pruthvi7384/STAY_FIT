<template>
   <div class="heading">
        <p>Calculate BMI</p>
    </div>
    <div class="panal">
        <div class="request_form">
            <div class="heading">
                <h2>Request - BMI Calculation</h2>
            </div>
            <div class="form">
                <div class="mb-3" v-for="(data,index) in request" :key="index">
                    <label :for="'ex'+index" class="form-label">{{data.name}}</label>
                    <input :type="data.type" v-model="data.value" class="form-control" :id="'ex'+index" :placeholder="'Enter '+data.name">
                </div>
                <div class="mt-4">
                    <button class="btn m-1" @click="getBMI()">{{this.loder ? "Processing....":"Send Request"}}</button>
                    <button class="btn m-1" style="background-color: red" @click="clear()">Clear Now</button>
                </div>
            </div>
        </div>
        <div class="response">
            <div class="heading">
                <h2>Report - BMI</h2>
            </div>
            <ul class="response_row">
                <li v-for="(data,index) in response" :key="index">
                    {{data.name}} : <span>{{data.value == "" ? 'NA' : data.value}}</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import server from '../../../server/backend'
export default {
    name:"CalculateBMI",
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
                }
            ],
            response:[
                {
                    name:"BMI",
                    value:""
                },
                {
                    name:"Health Status",
                    value:""
                },
                {
                    name:"Health BMI Range",
                    value:""
                }
            ],
            loder: false
        }
    },
    methods:{
        // ============= Calculate BMI ============
            async getBMI(){
                this.loder = true
                try{
                    const serverResponse = await server.getBMI(this.request[0].value, this.request[1].value,  this.request[2].value);
                    this.response = [
                        {
                            name:"BMI",
                            value:serverResponse.data.data.bmi
                        },
                        {
                            name:"Health Status",
                            value:serverResponse.data.data.health
                        },
                        {
                            name:"Health BMI Range",
                            value:serverResponse.data.data.healthy_bmi_range
                        }
                    ]
                    console.log("BMI - ",this.response);
                    this.loder = false;
                }catch(e){
                    console.log("Error - ",e);
                    this.loder = false;
                }
            },
        // ========X==== Calculate BMI ===X========

        // ============= Clear Data ==============
            clear(){
                this.request=[
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
                    }
                ],
                this.response=[
                    {
                        name:"BMI",
                        value:""
                    },
                    {
                        name:"Health Status",
                        value:""
                    },
                    {
                        name:"Health BMI Range",
                        value:""
                    }
                ]
            }
        // ========X==== Clear Data ====X=========
    }
}
</script>