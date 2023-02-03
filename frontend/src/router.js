import { createRouter, createWebHashHistory } from "vue-router"
import RegisterAction from "./views/account/Register"
import LogIn from "./views/account/Login"
import Home from "./views/Home"
import Dashboard from "./views/dashboard/Dashboard"
import VisuliZation from "./views/dashboard/visulization/Visulization"
import CheckIdealWeight from "./views/dashboard/checkIdealWeight/CheckWeight"
import CalculateBMI from "./views/dashboard/CalculateBMI/CalculateBMI"
import BodyFatCalculater from "./views/dashboard/bodyFatCalculate/BodyFatCalculate"
import DailyCaloryRequirment from "./views/dashboard/caloryRequirment/DailyCaloryRequirment"
import CaloriesBurnedByActivity from "./views/dashboard/caloriesBurnedByActivity/CaloriesBurnedByActivity"
import Profile from "./views/dashboard/Profile/Profile"
const routes = [
    {
        path: "/",
        name:"HomeScreen",
        component : Home
    },
    {
        path : "/registration",
        name : "RegisterAction",
        component : RegisterAction
    },
    {
        path : "/login",
        name : "LogIn",
        component : LogIn
    },
    {
        path: "/dashboard",
        name:"Dashboard",
        component : Dashboard,
        children:[
            {
                path:"/dashboard",
                name:"VisuliZation",
                component:VisuliZation
            },
            {
                path:"/checkidealweight",
                name:"checkIdealWeight",
                component:CheckIdealWeight
            },
            {
                path:"/calculatebmi",
                name:"CheckCaloriesRequirment",
                component:CalculateBMI
            },
            {
                path:"/calculatebodyfat",
                name:"CalculateBMI",
                component:BodyFatCalculater
            },
            {
                path:"/caloryrequirments",
                name:"DailyCaloryRequirment",
                component:DailyCaloryRequirment
            },
            {
                path:"/caloryburnedbyactivity",
                name:"CaloriesBurnedByActivity",
                component:CaloriesBurnedByActivity
            },
            {
                path:"/profile",
                name:"ProFile",
                component:Profile
            }
        ]
    }
    
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;