let Address = {
    data() {
        return {
            username: '',
            tableData: [{
                addressId: '123',
                region: '湖南科技大学',
                pickUpAddress: '北门步步高右侧菜鸟驿站',
                receiveAddress: '7区10栋101',
            }, {
                addressId: '123',
                region: '湘潭大学',
                pickUpAddress: '北门步步高右侧菜鸟驿站',
                receiveAddress: '7区10栋101',
            }],
            dialogVisible: false,
            regions: '',
            form: {
                regionValue: '',
                pickUpAddress: '北门步步高右侧菜鸟驿站',
                receiveAddress: '7区10栋101',
            }

        }
    },
    methods: {
        //编辑
        handleEdit(index, row) {
            this.dialogVisible = true;
            console.log(row.addressId)
        },

        //删除
        handleDelete(index, row) {
            this.$confirm('此操作将永久删除地址，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$message({
                    type: 'success',
                    message: '删除成功',
                })
            })
        }
    },
    //生命期钩子
    created() {
        setUsername(this);
        getRegions(this);
    },
}

let address = Vue.createApp(Address);
address.use(ElementPlus);
const app = address.mount('#address');

window.onload = function () {
    /*setUsername(app);
    getRegions(app);*/
}