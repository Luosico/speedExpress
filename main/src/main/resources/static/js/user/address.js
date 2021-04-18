let Address = {
    data() {

        return {
            username: 'luosico',
            tableData: [{
                region: '湖南科技大学',
                detailAddress: 'abc'
            }, {
                    region: '湘潭大学',
                    detailAddress: 'abc'
                }],
            dialogVisible: false,
            form: {
                regions: [{
                    label: '湖南科技大学',
                    value: '001'
                }, {
                    label: '湘潭大学',
                    value: '002'
                }],
                regionValue: '',
                detailAddress: '雨湖区',
            }

        }
    },
    methods: {
        //编辑
        handleEdit(index, row) {
            this.dialogVisible = true;
            console.log('index: ' + index, row);
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
    watch: {}
}

let address = Vue.createApp(Address);
address.use(ElementPlus);
address.mount('#address');