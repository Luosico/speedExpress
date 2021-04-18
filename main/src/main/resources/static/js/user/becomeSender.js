const App = {
    data() {

        return {
            username: 'luosico',
            form: {
                name: '张三',
                identityNumberId: '',
                phoneNumber: '',
                smsCode: ''
            }
        }
    },
    methods: {}
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#becomeSender');