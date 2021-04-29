const Main = {
    data() {
        return {
            username: '',
            unfinishedOrder: '1',
            finishedOrder: '1',
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
    }
};

let main = Vue.createApp(Main);
main.use(ElementPlus);
const app = main.mount('#main');