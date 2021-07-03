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
        },
        expressType(type){
            switch (type) {
                case 'SMALL':
                    return '小';
                case 'MEDIUM':
                    return '中';
                case 'LARGE':
                    return '大';
            }
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
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');