const App = {
    data() {
        return {
            username: 'admin',
            tableData: [{
                orderId: '202104171417531',
                regionName: '湖南科技大学',
                detailedAddress: '南校图书馆旁小堕落街口京东派',
                destination: '7区10栋101',
                courierNumber: '162758631105',
                name:'张三',
                courierCompany: '京东',
                courierType: '一类',
                courierCode: '41-103',
                phoneNumber: '15200010002',
                courierName: '张三',
                courierPhoneNumber: '1520001002',
                createTime: '2021-04-17 14:21',
                payId: '202104171417531',
                money: 2,
                status: '等待接单',

            }, {
                orderId: '202104171417532',
                regionName: '湖南科技大学',
                detailedAddress: '南校图书馆旁小堕落街口京东派',
                destination: '7区10栋101',
                courierNumber: '162758631105',
                name:'张三',
                courierCompany: '京东',
                courierType: '一类',
                courierCode: '41-103',
                phoneNumber: '15200010002',
                courierName: '张三',
                courierPhoneNumber: '1520001002',
                createTime: '2021-04-18 14:21',
                payId: '202104171417531',
                money: 1,
                status: '已确认收货',
            }]
        }
    },
    methods: {
        handleEdit(index, row){

        }
    },
    computed: {}
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#app');