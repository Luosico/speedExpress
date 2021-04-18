const Order = {
    data() {
        return {
            username: 'lootalker',
            tableData: [{
                orderId: '202104171417531',
                regionName: '湖南科技大学',
                detailedAddress: '南校图书馆旁小堕落街口京东派',
                courierNumber: '162758631105',
                courierCompany: '京东',
                courierCode: '41-103',
                courierName: '张三',
                createTime: '2021-04-17 14:21',
                payId: '202104171417531',
                money: 2,
                status: '等待接单',

            }, {
                orderId: '202104171417532',
                regionName: '湖南科技大学',
                detailedAddress: '南校图书馆旁小堕落街口京东派',
                courierNumber: '162758631105',
                courierCompany: '京东',
                courierCode: '41-103',
                courierName: '张三',
                createTime: '2021-04-18 14:21',
                payId: '202104171417531',
                money: 1,
                status: '已接单',
            }],
        }
    },
    methods: {
        getStatus(status) {
            switch (status) {
                case '等待接单':
                    return 'danger';
                case '已接单' :
                    return 'warning';
                case '配送中' :
                    return '';
                case '配送完成' :
                    return 'success';
                default:
                    return 'info';
            }
        }
    },
    computed: {}
}

let order = Vue.createApp(Order);
order.use(ElementPlus);
order.mount('#order');