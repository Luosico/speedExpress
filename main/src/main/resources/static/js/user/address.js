let Address = {
    data() {
        let validateRegion = (rule, value, callback) => {
            if (!value || value === '') {
                callback(new Error('区域不能为空, 请选择区域'));
            } else {
                callback();
            }

        };
        let validatePickUpAddress = (rule, value, callback) => {
            if (!value || value === '') {
                callback(new Error('取件地址不能为空！'));
            } else {
                callback();
            }

        };
        let validateReceiveAddress = (rule, value, callback) => {
            if (!value || value === '') {
                callback(new Error('收件地址不能为空！'));
            } else {
                callback();
            }

        };
        return {
            loading: false,
            username: '',
            tableData: '',
            dialogVisible: false,
            regions: '',
            form: {
                addressId: '',
                regionValue: '',
                pickUpAddress: '',
                receiveAddress: '',
            },
            dialog: {
                addressId: '',
                regionId: '',
                pickUpAddress: '',
                receiveAddress: '',
            },
            rules: {
                regionValue: [
                    {validator: validateRegion, trigger: 'change'},
                ],
                pickUpAddress: [
                    {validator: validatePickUpAddress, trigger: 'blur'}
                ],
                receiveAddress: [
                    {validator: validateReceiveAddress, trigger: 'blur'}
                ],
            }
        }
    },
    methods: {
        /**
         * 添加地址
         */
        addAddress() {
            this.$refs['form'].validate((valid) => {
                //表单校验成功
                if (valid) {
                    let data = {
                        regionId: this.form.regionValue,
                        pickUpAddress: this.form.pickUpAddress,
                        receiveAddress: this.form.receiveAddress,
                    }

                    //提交表单
                    addAddress(data, this);
                }
            })
        },

        //编辑
        handleEdit(row) {
            this.dialogVisible = true;

            this.dialog.addressId = row.addressId;
            this.dialog.regionId = row.regionId;
            this.dialog.pickUpAddress = row.pickUpAddress;
            this.dialog.receiveAddress = row.receiveAddress;
        },

        //删除
        handleDelete(row) {
            this.$confirm('此操作将永久删除地址，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let addressId = row.addressId;
                deleteAddress(addressId, this);
            })
        },

        /**
         * 更新地址
         */
        submitUpdate() {
            let data = {
                addressId: this.dialog.addressId,
                regionId: this.dialog.regionId,
                pickUpAddress: this.dialog.pickUpAddress,
                receiveAddress: this.dialog.receiveAddress
            }
            //更新地址
            updateAddress(data, this);
            //对话框消失
            this.dialogVisible = false;
        }
    },
    //生命期钩子
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        });
        getRegions(this, (vue, response) => {
            vue.regions = response.data;
        });
        getAddresses(this, (vue, response) => {
            vue.tableData = response.data;
        });
    },
}

let address = Vue.createApp(Address);
address.use(ElementPlus);
const app = address.mount('#address');