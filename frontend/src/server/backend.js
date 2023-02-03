import axios from "axios";

const baseUrl = "http://localhost:9000/";
const restApi = "https://fitness-calculator.p.rapidapi.com/";
const key = "a75a5acfc0msh1309774f8099d26p1bdc84jsn5606758833a4";
const host = "fitness-calculator.p.rapidapi.com";

const backend = {
     registerServer(request){
        return axios.post(baseUrl+'api/user/registration',request);
     },
     loginServer(request){
          return axios.post(baseUrl+'api/user/login',request);
     },
     getIdealWeight(gender,height,){
          const options = {
               method: 'GET',
               url: restApi+'idealweight',
               params: {gender: gender, height: height},
               headers: {
                 'X-RapidAPI-Key': key,
                 'X-RapidAPI-Host': host
               }
             };
         return axios.request(options)
     },
     getBMI(age,weight,height){
          const options = {
               method: 'GET',
               url: restApi+'bmi',
               params: {age: age, weight: weight, height: height},
               headers: {
                 'X-RapidAPI-Key': key,
                 'X-RapidAPI-Host': host
               }
             };
         return axios.request(options)
     },
     getFatPersentage(request,gender){
          const options = {
               method: 'GET',
               url: restApi+'bodyfat',
               params: {
                    age: request[0].value,
                    gender: gender,
                    weight: request[1].value,
                    height: request[2].value,
                    neck: request[3].value,
                    waist: request[4].value,
                    hip: request[5].value
                  },
               headers: {
                 'X-RapidAPI-Key': key,
                 'X-RapidAPI-Host': host
               }
             };
         return axios.request(options)
     },
     getCaloryRequirments(request,level,gender){
          const options = {
               method: 'GET',
               url: restApi+'dailycalorie',
               params: {
                    age: request[0].value,
                    gender: gender,
                    height: request[1].value,
                    weight: request[2].value,
                    activitylevel: level
                  },
               headers: {
                 'X-RapidAPI-Key': key,
                 'X-RapidAPI-Host': host
               }
             };
         return axios.request(options)
     },
     getCaloriesBurndByActivity(activity,weight,duration){
      const options = {
           method: 'GET',
           url: 'https://calories-burned-by-api-ninjas.p.rapidapi.com/v1/caloriesburned',
           params: {
                activity: activity, 
                weight: weight, 
                duration: duration
              },
           headers: {
            'X-RapidAPI-Key': 'a75a5acfc0msh1309774f8099d26p1bdc84jsn5606758833a4',
            'X-RapidAPI-Host': 'calories-burned-by-api-ninjas.p.rapidapi.com'
           }
         };
     return axios.request(options)
    },
    getProfile(){
      const token = localStorage.getItem("ACCESS_TOKEN");
      const options = {
        method: "GET",
        url : baseUrl+"api/user/profile/view",
        headers: {
          'Authorization': 'Bearer '+token
        }
      }
      return axios.request(options)
    }

}

export default backend;