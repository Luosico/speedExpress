const App = {
    data() {
        return {
            username: '',
            regionData: '',
            form: {
                regionName: ''
            },
            dialog:{
                regionId: '',
                regionName: ''
            },
            dialogVisible: false,
        }
    },
    methods: {
        addRegion() {
            let regionName = this.form.regionName;
            let vue = this;
            axios({
                url: '/admin/region',
                method: 'POST',
                data: {
                    regionName: regionName
                }
            }).then((response) => {
                if (response.data.status === 'ok'){
                    refresh()
                }else{
                    vue.$message({
                        type: 'error',
                        message: response.data.message
                    })
                }
            })
        },
        dialogShow(row){
            this.dialogVisible = true;
            this.dialog.regionId = row.regionId;
            this.dialog.regionName = row.regionName;
        },
        //更改区域
        handleEdit(){
            let regionId = this.dialog.regionId;
            let regionName = this.dialog.regionName;
            let vue = this;

            axios({
                url: "/admin/region",
                method: "PUT",
                data: {
                    regionId: regionId,
                    regionName: regionName
                }
            }).then(function (response) {
                if(response.data.status === 'ok'){
                    refresh();
                }else{
                    vue.$message({
                        type: 'error',
                        message: response.data.message
                    })
                }
            })
        },
        //删除区域
        handleDelete(row){
            let regionId = row.regionId;
            let vue =this;

            axios({
                url: "/admin/region",
                method: "DELETE",
                data: {
                    regionId: regionId,
                }
            }).then(function (response) {
                if(response.data.status === 'ok'){
                    refresh();
                }else{
                    vue.$message({
                        type: 'error',
                        message: response.data.message
                    })
                }
            })
        }
    },
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        });
        getRegions(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.regionData = response.data;
            }
        })
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
let ap = app.mount('#app');