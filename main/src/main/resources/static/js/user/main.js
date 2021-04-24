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

    }
};

let main = Vue.createApp(Main);
main.use(ElementPlus);
const app = main.mount('#main');

//页面加载完成
window.onload = function (){
    setUsername(app);
}