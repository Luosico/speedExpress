const App = {
    data() {
        return {
            username: '',
            tableData: ''
        }
    },
    methods: {
        deleteOrder(row) {
            let orderId = row.orderId;
            this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteOrder(orderId, this, (vue, response)=>{
                    if(response.status === 'ok'){
                        vue.$message({
                            type: 'success',
                            message: '删除成功'
                        })
                    }else{
                        vue.$message({
                            type: 'error',
                            message: response.message
                        })
                    }
                })
            })
        }
    },
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        });
        selectOrder(['UN_ACCEPT_ORDER'], this, (vue, response) => {
            if (response.status === 'ok'){
                vue.tableData = response.data;
            }else{
                vue.$message({
                    type: 'error',
                    message: '页面异常，服务器出现错误'
                })
            }
        })
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');