const AddOrder = {
    data(){

        return{
            username:'luosico',
            form: {
                regionValue: '',
                regions: [{
                    label: '湖南科技大学',
                    value: '001'
                }, {
                    label: '湘潭大学',
                    value: '002'
                }],
                detailAddress: '',
                name: "",
                phoneNumber:'',
                courierNumber:'',
                courierCompany:'',
                courierCode:'',
                courierType:'',
                sendAddress:'',
            },
        }
    },
    methods:{

    },
    watch:{

    }
}

let a = Vue.createApp(AddOrder);
a.use(ElementPlus);
a.mount('#addOrder');