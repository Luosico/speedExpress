let Main = {
    data(){

        return {
            username:'luosico',
            unfinishedOrder: '1',
            finishedOrder:'1',
            feedback:'1',
        };
    },
    methods:{

    },
    watch:{

    }
};

let main = Vue.createApp(Main);
main.use(ElementPlus);
main.mount('#main');