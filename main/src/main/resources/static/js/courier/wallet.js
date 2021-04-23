const App = {
    data() {
        return {
            username: 'lootalker',
            balance: '0.00',
        }
    },
    methods: {

    },
    computed: {}
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');