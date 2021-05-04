const App = {
    data() {
        return {
            username: '',
            tableData: ''
        }
    },
    methods: {
        deleteOrder(row) {

        }
    },
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        });
        selectOrder(['DELIVERY_ORDER'], this, (vue, response) => {
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