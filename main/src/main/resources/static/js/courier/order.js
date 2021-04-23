const Order = {
    data() {
        return {
            username: 'lootalker',
            tableData: [{
                orderId: '202104171417531',
                regionName: '湖南科技大学',
                detailedAddress: '南校图书馆旁小堕落街口京东派',
                destination: '7区10栋101',
                courierNumber: '162758631105',
                name: '张三',
                courierCompany: '京东',
                courierType: '一类',
                courierCode: '41-103',
                phoneNumber: '15200010002',
                courierName: '张三',
                courierPhoneNumber: '1520001002',
                createTime: '2021-04-17 14:21',
                payId: '202104171417531',
                money: 2,
                status: '配送完成',
            }, {
                orderId: '202104171417532',
                regionName: '湖南科技大学',
                detailedAddress: '南校图书馆旁小堕落街口京东派',
                destination: '7区10栋101',
                courierNumber: '162758631105',
                name: '张三',
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
                    return 'info';
                default:
                    return 'success';
            }
        },
        /**
         * 确认收货
         */
        getExpressStatus(status) {
            if (status === "已确认收货") {
                return false
            }
            return true;
        },
        handleEdit(index, row) {

        }
    },
    computed: {}
}

let order = Vue.createApp(Order);
order.use(ElementPlus);
order.mount('#order');