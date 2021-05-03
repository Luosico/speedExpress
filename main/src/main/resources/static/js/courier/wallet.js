const App = {
    data() {
        return {
            username: '',
            totalBalance: '0.00',
            balance: '0.00',
            dialogVisible: false,
            form: {
                amount: '',
            }
        }
    },
    methods: {
        /**
         * 提现
         */
        getMoney() {
            let amount = this.form.amount;
            let vue = this;
            axios({
                url: '/courier/getMoney',
                method: 'POST',
                data: {
                    amount: amount
                }
            }).then((response) => {
                if(response.data.status === 'ok'){
                    vue.$message({
                        type: 'success',
                        message: '提现成功'
                    })
                }else{
                    vue.$message({
                        type:'error',
                        message:response.data.message
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
        getTotalBalance(this);
        getBalance(this);
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');