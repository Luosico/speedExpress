const App = {
    data() {
        return {
            username: '',
            tableData: '',
            loading: false,
        }
    },
    methods: {
        /**
         * 抢单
         */
        tryAcceptOrder(row) {
            this.loading = true;
            let orderId = row.orderId;
            tryAcceptOrder(orderId, this, (vue, response) => {
                this.loading = false;
                if(response.status === 'ok'){
                    vue.$message({
                        type: 'success',
                        message: '接单成功'
                    })
                }else{
                    vue.$message({
                        type:'warning',
                        message: response.message
                    })
                }
            })
        }
    },
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        })
        getUnAcceptOrder(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.tableData = response.data;
            } else {
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })
        /*selectOrder(['UN_ACCEPT_ORDER'], this, (vue, response) => {
            if (response.status === 'ok') {
                vue.tableData = response.data;
            } else {
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })*/
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');