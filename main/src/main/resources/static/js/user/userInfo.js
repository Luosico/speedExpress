let Main = {
    data(){

        return {
            username:'luosico',
            name: '张三',
            newPassword:'',
            repeatPassword:'',
            smsCodePassword:'',
            smsCodePhoneNumber:'',
            phoneNumber: '',
        }
    },
    methods:{

    },
    watch:{

    }
};
let main = Vue.createApp(Main);
main.use(ElementPlus);
main.mount('#userInfo');


/*
let main = Vue.extend(Main);
new main().$mount('#userInfo');*/
