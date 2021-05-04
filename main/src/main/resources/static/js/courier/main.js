const App = {
    data() {

        return {
            username: 'luosico',
            acceptedOrder: '',
            deliveryOrder: '',
            finishedOrder: '',
            totalBalance: '',
        }
    },
    methods: {},
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        });
        countCourierOrder(this, (vue, response) => {
            vue.acceptedOrder = response.data.acceptedOrder;
            vue.deliveryOrder = response.data.deliveryOrder;
            vue.finishedOrder = response.data.finishedOrder;
        })
        getCourierTotalBalance(this);
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');