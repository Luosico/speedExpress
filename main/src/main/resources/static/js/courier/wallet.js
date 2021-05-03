const App = {
    data() {
        return {
            username: '',
            balance: '0.00',
            total: '0.00'
        }
    },
    methods: {

    },
    created(){
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        })
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');