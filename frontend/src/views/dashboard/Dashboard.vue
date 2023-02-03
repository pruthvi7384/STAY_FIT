<template>
  <AfterLoginTop/>
  <div class="container-fluid dashboard">
    <div class="row">
        <div class="col-xl-2 left_side_bar">
          <ul>
            <li v-for="(data, index) in sidebar" :key="index">
                <router-link :to="data.to" exactActiveClass="active" class="btn">{{data.name}}</router-link>
            </li>
          </ul>
        </div>
        <div class="col-xl-10 right_side_bar">
            <router-view/>
        </div>
    </div>
  </div>
</template>

<script>
  import AfterLoginTop from "../../components/AfterLoginTop"
    export default {
        name:"DashBoard",
        components:{
          AfterLoginTop
        },
        data(){
        return{
          sidebar:[
              {
                name:"Dashboard",
                to:"/dashboard"
              },
              {
                name:"Check Ideal Weight",
                to:"/checkidealweight"
              },
              {
                name:"Calculate BMI",
                to:"/calculatebmi"
              },
              {
                name:"Calculate Body Fat Percentage",
                to:"/calculatebodyfat"
              },
              {
                name:"Daily Calory Requirment",
                to:"/caloryrequirments"
              },
              {
                name:"Calories Burned By Activities",
                to:"/caloryburnedbyactivity"
              },
              {
                name:"Profile",
                to:"/profile"
              }
          ]
        }
      }
    }
</script>
