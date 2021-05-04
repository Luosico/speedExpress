const App = {
    data() {
        return {
            username: '',
            unAcceptOrder: '',
            acceptOrder: '',
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
        getTotalBalance(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.totalBalance = response.data;
            } else {
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })
        countTotalOrder(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.unAcceptOrder = response.data.UN_ACCEPT_ORDER;
                vue.acceptOrder = response.data.ACCEPTED_ORDER;
                vue.deliveryOrder = response.data.DELIVERY_ORDER;
                vue.finishedOrder = response.data.FINISHED_ORDER;
            }
        })
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');