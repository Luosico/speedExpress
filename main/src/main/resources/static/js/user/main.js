const Main = {
    data(){
        return {
            username:'',
            unfinishedOrder: '1',
            finishedOrder:'1',
            feedback:'1',
        };
    },
    methods:{

    },
    watch:{

    },
    created(){
        getUsername((val) =>{
            if(val.status === 'ok'){
                app.username = val.data;
            }
        });
    }
};

let main = Vue.createApp(Main);
main.use(ElementPlus);
const app = main.mount('#main');