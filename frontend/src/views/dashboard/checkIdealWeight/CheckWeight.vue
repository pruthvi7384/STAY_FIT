<template>
    <div class="heading">
        <p>Check Ideal Weight</p>
    </div>
    <div class="panal">
        <div class="request_form">
            <div class="heading">
                <h2>Request - Ideal Weight</h2>
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
                    <button class="btn m-1" @click="getIdealWeight()">{{this.loder ? "Processing....":"Send Request"}}</button>
                    <button class="btn m-1" style="background-color: red" @click="clear()">Clear Now</button>
                </div>
            </div>
        </div>
        <div class="response">
            <div class="heading">
                <h2>Report - Ideal Weight</h2>
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
        name:"checkIdealWeight",
        data(){
            return{
                request:[
                    {
                        name:"Height",
                        value:""
                    }
                ],
                gender : "",
                response:[
                    {
                        name:"Devine",
                        value:""
                    },
                    {
                        name:"Hamwi",
                        value:""
                    },
                    {
                        name:"Miller",
                        value:""
                    },
                     {
                        name:"Robinson",
                        value:""
                    }
                ],
                loder: false
            }
        },
        methods:{
            // =========== Ideal Weight From API ============
                async getIdealWeight(){
                    this.loder = true
                    try{
                    const serverResponse = await server.getIdealWeight(this.gender, this.request[0].value,);
                    this.response = [
                            {
                                name:"Devine",
                                value:serverResponse.data.data.Devine
                            },
                            {
                                name:"Hamwi",
                                value:serverResponse.data.data.Hamwi
                            },
                            {
                                name:"Miller",
                                value:serverResponse.data.data.Miller
                            },
                            {
                                name:"Robinson",
                                value:serverResponse.data.data.Robinson
                            }
                    ]
                    console.log("IDEAL WEIGHT - ",this.response);
                    this.loder = false;
                    }catch(e){
                        console.log("Error - ",e);
                        this.loder = false;
                    }
                },
            // ======X==== Ideal Weight From API ====X=======

            // =========== Clear Data ==============
                clear(){
                        this.request=[
                        {
                                name:"Gender",
                                value:""
                            },
                            {
                                name:"Height",
                                value:""
                            }
                        ],
                        this.response = [
                                {
                                    name:"Devine",
                                    value:""
                                },
                                {
                                    name:"Hamwi",
                                    value:""
                                },
                                {
                                    name:"Miller",
                                    value:""
                                },
                                {
                                    name:"Robinson",
                                    value:""
                                }
                        ]
                }
            // =======X==== Clear Data ====X=========
        }
    }
</script>
