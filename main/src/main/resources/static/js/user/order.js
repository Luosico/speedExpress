const Order = {
    data() {
        return {
            username: '',
            tableData: '',
        }
    },
    methods: {
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
         * 确认收货
         */
        getExpressStatus(status) {
            if (status === "CONFIRMED_ORDER") {
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
        }
    },
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        })
        getUserOrders(this, (vue, response) => {
            if(response.status === 'ok'){
                vue.tableData = response.data;
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