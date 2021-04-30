const AddOrder = {
    data() {
        let validateName = (rule, value, callback) => {
            if (!value) {
                callback(new Error('姓名不能为空'));
            } else if (!/^[\p{sc=Han}]*$/u.test(value)) {
                callback(new Error('字符格式有误'));
            }
            callback();
        };
        let validatePhoneNumber = (rule, value, callback) => {
            if (!value) {
                callback(new Error('手机号码不能为空'))
            } else if (!/^\d*$/.test(value)) {
                callback(new Error('手机号码格式不正确'))
            } else if (value.length < 6) {
                callback(new Error('不少于 6 位'))
            } else {
                callback();
            }
        };
        let validateExpressNumber = (rule, value, callback) => {
            if (!value) {
                callback(new Error('快递编号不能为空'));
            } else if (!/^[\w-]*$/.test(value)) {
                callback(new Error('格式有误'));
            }
            callback();
        };
        let validateExpressCompany = (rule, value, callback) => {
            if (!value) {
                callback(new Error('快递公司不能为空'));
            } else if (!/^[\p{sc=Han}]*$/u.test(value)) {
                callback(new Error('字符格式有误'));
            }
            callback();
        };
        let validateExpressCode = (rule, value, callback) => {
            if (!value) {
                callback(new Error('取件码不能为空'));
            } else if (!/^[\w\s-]*$/.test(value)) {
                callback(new Error('格式有误'));
            }
            callback();
        };
        let validateExpressType = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请选择快递类型'));
            }
            callback();
        };
        let validateFee = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请输入金额; 公益代取请填入 0 '))
            }else if(!/^\d[\d.]*$/.test(value)){
                callback(new Error('金额的格式有误'))
            }
            callback();
        };
        return {
            username: '',
            dialogVisible: false,
            dialogPayVisible: false,
            tableData: '',
            regions: '',
            form: {
                addressId: '',
                regionValue: '',
                pickUpAddress: '',
                name: "",
                phoneNumber: '',
                expressNumber: '',
                expressCompany: '',
                expressCode: '',
                expressType: '',
                receiveAddress: '',
                fee: '',
                remark: '',
            },
            expressTypes: [
                {
                    label: '小：  重量较轻或体积较小',
                    value: 'SMALL'
                },
                {
                    label: '中：  重量中等或体积中等',
                    value: 'MEDIUM'
                },
                {
                    label: '大：  重量较重或体积较大',
                    value: 'LARGE'
                }
            ],
            formRules: {
                name: [
                    {validator: validateName, trigger: "blur"}
                ],
                phoneNumber: [
                    {validator: validatePhoneNumber, trigger: "blur"}
                ],
                expressNumber: [
                    {validator: validateExpressNumber, trigger: "blur"}
                ],
                expressCompany: [
                    {validator: validateExpressCompany, trigger: "blur"}
                ],
                expressCode: [
                    {validator: validateExpressCode, trigger: "blur"}
                ],
                expressType: [
                    {validator: validateExpressType, trigger: "blur"}
                ],
                fee: [
                    {validator: validateFee, trigger: "blur"}
                ]
            }
        }
    },
    methods: {
        selectAddress() {
            this.dialogVisible = true;
        },
        //选择地址
        handleSelect(row) {
            this.form.addressId = row.addressId;
            this.form.regionName = row.regionName;
            this.form.pickUpAddress = row.pickUpAddress;
            this.form.receiveAddress = row.receiveAddress;

            this.dialogVisible = false;
        },

        /**
         * 验证订单
         */
        validateOrder() {
            this.$refs['form'].validate((valid) => {
                //全部校验成功
                if (this.validateAddress() === true && valid) {
                    this.dialogPayVisible = true;
                }
            })
        },

        /**
         * 提交订单
         */
        submitOrder() {
            let form = {
                addressId: this.form.addressId,
                name: this.form.name,
                phoneNumber: this.form.phoneNumber,
                expressNumber: this.form.expressNumber,
                expressCompany: this.form.expressCompany,
                expressCode: this.form.expressCode,
                expressType: this.form.expressType,
                fee: this.form.fee,
                remark: this.form.remark
            }
            let data = form;

            addOrder(data, this, (vue, response) => {
                if (response.status === 'ok') {
                    vue.dialogPayVisible = false;
                    vue.$message({
                        type: 'success',
                        message: '订单创建成功'
                    })
                } else {
                    vue.$message({
                        type: "error",
                        message: response.message
                    })
                }
            })
        },

        /**
         * 地址不能为空
         */
        validateAddress() {
            if (this.form.pickUpAddress === '') {
                this.$alert('请选择您的地址', '选择地址', {
                    confirmButtonText: '确定',
                })
                return false;
            } else {
                return true;
            }

        },
        /**
         * 清空表单
         */
        clearForm() {
            this.$refs['form'].resetFields();
        }
    },
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        })
        getName(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.form.name = response.data;
            }
        })
        getPhoneNumber(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.form.phoneNumber = response.data;
            }
        })
        getAddresses(this, (vue, response) => {
                if (response.status === 'ok') {
                    vue.tableData = response.data;

                    if (vue.tableData == '') {
                        vue.$alert('您还未添加地址', "请前去添加地址", {
                            confirmButtonText: '确定',
                            callback: action => {
                                window.location.href = "address";
                            }
                        })
                    }
                    //获取区域
                    getRegions(this, (vue, response) => {
                        if (response.status === 'ok') {
                            let regions = response.data;
                            //替换regionId为regionName
                            let data = vue.tableData;
                            for (let i = 0; i < data.length; i++) {
                                let regionId = data[i].regionId;
                                let regionName = getRegionName(regionId, regions);
                                //增加属性
                                Object.defineProperty(data[i], 'regionName', {value: regionName, writable: true});
                            }
                        }
                    })
                }
            }
        )
    }
}

let a = Vue.createApp(AddOrder);
a.use(ElementPlus);
let app = a.mount('#addOrder');