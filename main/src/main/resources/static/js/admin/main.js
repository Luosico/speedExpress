const App = {
    data() {
        return {
            username: 'admin',
            unAcceptOrder: '10',
            acceptOrder: '10',
            deliveryOrder: '20',
            finishedOrder:'30',
            cumulativeAmount: '500',
        }
    },
    methods: {

    },
    computed: {}
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');