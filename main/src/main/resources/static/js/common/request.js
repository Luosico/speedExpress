/**
 * 刷新页面
 */
function refresh() {
    window.location.reload();
}

/**
 * 获取 username
 * 更新 Vue实例的 username
 * @param vue Vue实例
 */
function setUsername(vue) {
    axios({
        url: "/common/username",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        vue.username = response.data.username
    })
}

/**
 * 获取区域
 */
function getRegions(vue) {
    axios({
        url: "/common/regions",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        vue.regions = response.data.regions;
    })
}

/**
 * 添加地址
 * @param data 地址信息
 */
function addAddress(data, vue) {
    axios({
        url: "/common/address",
        method: "POST",
        data: data,
        responseType: "json",
    }).then(function (response) {
        if(requestSuccess(response)){
            refresh();
        }else{
            vue.$message({
                message: '提交失败',
                type: 'error'
            });
        }
    })
}

/**
 * 获取所有地址
 * @param vue
 */
function getAddresses(vue) {
    axios({
        url: "/common/address",
        method: "GET",
        responseType: "json",
    }).then(function (response) {
        vue.tableData = response.data.data;
    })
}

/**
 * 更新地址
 * @param data
 */
function updateAddress(data, vue) {
    vue.loading = true;
    axios({
        url: "/common/address",
        method: "PUT",
        data: data,
        responseType: "json",
    }).then(function (response) {
        vue.loading = false;
        //更新成功
        if(requestSuccess(response)){
            getAddresses(vue);
            vue.$message({
                message: '地址更新成功',
                type: 'success'
            });
        }else{
            vue.$message({
                message: '地址更新失败',
                type: 'error'
            });
        }
    })
}

/**
 * 删除地址
 * @param addressId
 * @param vue
 */
function deleteAddress(addressId, vue) {
    vue.loading = true;
    axios({
        url: "/common/address",
        method: "DELETE",
        data: {
            addressId: addressId
        },
        responseType: "json",
    }).then(function (response) {
        vue.loading =false;
        if(requestSuccess(response)){
            refresh();
        }else {
            vue.$message({
                message: '地址删除失败',
                type: 'error'
            });
        }
    })
}

/**
 * 请求是否执行成功
 * true 成功
 * false 失败
 */
function requestSuccess(response) {
    return response.data.status === 'ok';
}