const App = {
    data(){

        return{
            username: 'luosico',
            acceptedOrder: '1',
            deliveryOrder: '1',
            finishedOrder: '1',
        }
    },
    methods: {

    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');