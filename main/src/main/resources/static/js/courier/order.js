const Order = {
    data() {
        return {
            username: '',
            acceptOrder: '',
            deliveryOrder: '',
            finishedOrder: '',
            confirmedOrder: '',
            myOrder: '',
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
        getStatus(status) {
            switch (status) {
                case 'UN_ACCEPT_ORDER':
                    return '等待接单';
                case 'ACCEPTED_ORDER' :
                    return '已接单';
                case 'DELIVERY_ORDER' :
                    return '派送中';
                case 'FINISHED_ORDER' :
                    return '派送完成';
                case 'CONFIRMED_ORDER' :
                    return '已确认收货';
            }
        },
        getStatusType(status) {
            switch (status) {
                case 'UN_ACCEPT_ORDER':
                    return 'danger';
                case 'ACCEPTED_ORDER' :
                    return 'warning';
                case 'DELIVERY_ORDER' :
                    return '';
                case 'FINISHED_ORDER' :
                    return 'success';
                default:
                    return 'info';
            }
        },
        /**
         * 取到快递
         * @param row
         */
        getExpress(row){
            let data = {
                orderId: row.orderId,
                orderStatus: 'DELIVERY_ORDER'
            }
            updateOrderStatus(data,this);
        },

        /**
         * 配送完成
         */
        finishOrder(row){
            let data = {
                orderId: row.orderId,
                orderStatus: 'FINISHED_ORDER'
            }
            updateOrderStatus(data,this);
        },
        /**
         * 确认收货
         * @param row
         */
        confirmReceived(row) {
            this.$confirm('您确定快递已送到了吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //订单编号
                let orderId = row.orderId;
                orderConfirmReceived(orderId, this, (vue, response) => {
                    if (response.status === 'ok') {
                        refresh();
                    } else {
                        vue.$message({
                            type: 'error',
                            message: response.message
                        })
                    }
                })
            })
        },
        expressType(type){
            switch (type) {
                case 'SMALL':
                    return '小';
                case 'MEDIUM':
                    return '中';
                case 'LARGE':
                    return '大';
            }
        }
    },
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        });
        //已接单
        selectCourierOrder(['ACCEPTED_ORDER'], this, (vue, response) => {
            if (response.status === 'ok') {
                vue.acceptOrder = response.data;
            } else {
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })
        //配送中
        selectCourierOrder(['DELIVERY_ORDER'], this, (vue, response) => {
            if (response.status === 'ok') {
                vue.deliveryOrder = response.data;
            } else {
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })
        //配送完成
        selectCourierOrder(['FINISHED_ORDER','CONFIRMED_ORDER'], this, (vue, response) => {
            if (response.status === 'ok') {
                vue.finishedOrder = response.data;
            } else {
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })
        //我的快递
        getUserOrders(this, (vue, response) => {
            if(response.status === 'ok'){
                vue.myOrder = response.data;
            }else{
                vue.$message({
                    type: 'error',
                    message: response.message
                })
            }
        })
    }
}

let order = Vue.createApp(Order);
order.use(ElementPlus);
order.mount('#order');