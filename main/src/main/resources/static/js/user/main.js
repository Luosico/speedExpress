const Main = {
    data() {
        return {
            username: '',
            unfinishedOrder: '',
            finishedOrder: '',
            feedback: '1',
        };
    },
    methods: {},
    watch: {},
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        });
        //未完成订单
        countOrder( ['UN_ACCEPT_ORDER', 'ACCEPTED_ORDER', 'DELIVERY_ORDER', 'FINISHED_ORDER'], this, (vue, response) => {
            if (response.status === 'ok') {
                vue.unfinishedOrder = response.data;
            } else {
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })
        //已完成订单
        countOrder(['CONFIRMED_ORDER'], this, (vue, response) => {
            if (response.status === 'ok') {
                vue.finishedOrder = response.data;
            } else {
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })

    }
};

let main = Vue.createApp(Main);
main.use(ElementPlus);
const app = main.mount('#main');